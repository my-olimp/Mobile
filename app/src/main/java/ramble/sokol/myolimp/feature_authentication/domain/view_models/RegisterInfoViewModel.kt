package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.destinations.RegisterEducationScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationInfoEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationInfoState
import ramble.sokol.myolimp.utils.BaseViewModel




class RegisterInfoViewModel : BaseViewModel<RegistrationInfoState>(RegistrationInfoState()) {

    companion object {
        private const val TAG : String = "RegistrationInfoViewModel"
    }

    private val repository = RegistrationRepository()

    fun onEvent(
        event: RegistrationInfoEvent
    ) {
        when(event) {
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
                    this.updateLoader(true)
                    sendRequest(navigator = event.navigator)
                }
            }
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
        navigator: DestinationsNavigator
    ) {
        launchIO {
            val fio = state.value.fio.split(" ")
            repository.registerInfo(
                data = UserMainDataModel(
                    firstName = fio[0],
                    secondName = fio[1],
                    thirdName = fio[2],
                    dateOfBirth = state.value.bdate,
                    gender = state.value.gender,
                    accountType = state.value.requestActivityType
                ),
                onResult = {
                    updateLoader(false)
                    Log.i(TAG, "response: $it")
                    if (it != null) {
                        navigator.navigate(
                            RegisterEducationScreenDestination(isWorkScreen = state.value.requestActivityType == "t")
                        )
                    } else castError()
                },
                onError = {
                    Log.i(TAG, "exception: ${it.message}")
                    castError()
                }
            )
        }
    }
}
