package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.RegisterEducationScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationInfoEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationInfoState

class RegisterInfoViewModel : ViewModel() {

    companion object {
        private const val TAG : String = "RegistrationInfoViewModel"
    }

    private val repository = RegistrationRepository()

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(
        RegistrationInfoState()
    )

    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationInfoEvent
    ) {
        when(event) {

            is RegistrationInfoEvent.OnCancelLoader -> {
                _state.update { it.copy(isLoading = false) }
            }
            is RegistrationInfoEvent.OnLoaderUp -> {
                _state.update { it.copy(isLoading = true) }
            }
            is RegistrationInfoEvent.OnActivityTypeChanged -> {
                _state.update {
                    it.copy(
                        requestActivityType = event.requestActivityType,
                        activityType = event.activityType,
                        activityTypeError = false
                    )
                }
            }
            is RegistrationInfoEvent.OnDobChanged -> {
                _state.update {
                    it.copy(
                        bdate = event.bdate,
                        bdateError = false
                    )
                }
            }
            is RegistrationInfoEvent.OnGenderChanged -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }
            is RegistrationInfoEvent.OnNameSurnameChanged -> {
                _state.update {
                    it.copy(
                        fio = event.fio,
                        fioError = false
                    )
                }
            }
            is RegistrationInfoEvent.OnNext -> {
                if(isDataValid()) {
                    onEvent(RegistrationInfoEvent.OnLoaderUp)
                    sendRequest(
                        onResult = {
                            onEvent(RegistrationInfoEvent.OnCancelLoader)
                            event.navigator.navigate(RegisterEducationScreenDestination(
                                isWorkScreen = state.value.requestActivityType == "t"
                            ))
                        },
                        onError = {
                            onEvent(RegistrationInfoEvent.OnCancelLoader)
                            Log.i(TAG,"request failed")
                        }
                    )
                }
            }
        }
    }

    private suspend fun updateUserData(
        userModel: UserMainDataModel,
        onResult: () -> Unit,
        onError: () -> Unit
    ) {
        try {
            repository.registerInfo(
                data = userModel,
                onResult = {
                    Log.i(TAG,"response: $it")
                    onResult.invoke()
                },
                onError = {
                    onError.invoke()
                    Log.i(TAG,"exception: ${it.message}")
                }
            )

        } catch (e : Exception){
            Log.i(TAG,"exception: ${e.message}")
        }
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if (state.value.fio.split(" ").size != 3) {
            _state.update { it.copy(fioError = true) }
            isValid = false
        }
        if(state.value.bdate == "") {
            _state.update { it.copy(bdateError = true) }
            isValid = false
        }
        if(state.value.requestActivityType == "") {
            _state.update { it.copy(activityTypeError = true) }
            isValid = false
        }
        return isValid
    }
    private fun sendRequest(
        onResult: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch (Dispatchers.IO) {
            val fio = state.value.fio.split(" ")
            updateUserData(
                userModel = UserMainDataModel(
                    firstName = fio[0],
                    secondName = fio[1],
                    thirdName = fio[2],
                    dateOfBirth = state.value.bdate,
                    gender = state.value.gender,
                    accountType = state.value.requestActivityType
                ),
                onResult = onResult,
                onError = onError
            )
        }
    }
}