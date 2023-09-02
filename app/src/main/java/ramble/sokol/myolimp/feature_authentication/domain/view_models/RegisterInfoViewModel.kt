package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationInfoRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationInfoState
import ramble.sokol.myolimp.feature_profile.data.Constants

class RegisterInfoViewModel(
    val context: Context
) : ViewModel() {

    companion object {
        private const val TAG : String = "RegistrationInfoViewModel"
    }

    private val repository = RegistrationInfoRepository()

    private val dataStore = CodeDataStore(context = context)

    private val _state = MutableStateFlow(
        RegistrationInfoState()
    )

    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationEvent
    ) {
        when(event) {
            is RegistrationEvent.OnActivityTypeChanged -> {
                _state.update {
                    it.copy(
                        activityType = event.activityType
                    )
                }
            }
            is RegistrationEvent.OnDobChanged -> {
                _state.update {
                    it.copy(
                        bdate = event.bdate
                    )
                }
            }
            is RegistrationEvent.OnGenderChanged -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }
            is RegistrationEvent.OnNameSurnameChanged -> {
                _state.update {
                    it.copy(
                        fio = event.fio
                    )
                }
            }
            is RegistrationEvent.OnNext -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val fio = state.value.fio.split(" ")
                    if(fio.size == 3) {
                        updateUserData(
                            UserMainDataModel(
                                firstName = fio[0],
                                secondName = fio[1],
                                thirdName = fio[2],
                                dateOfBirth = state.value.bdate,
                                gender = state.value.gender,
                                accountType = state.value.activityType
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun updateUserData(userModel: UserMainDataModel) {
        try {
            val response = repository.registerInfo(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN)?: throw Exception("No access token"),
                data = userModel
            )
            Log.i(TAG,"response is ${response.body()}")
        } catch (e : Exception){
            Log.i(TAG,"exception: ${e.message}")
        }
    }

}