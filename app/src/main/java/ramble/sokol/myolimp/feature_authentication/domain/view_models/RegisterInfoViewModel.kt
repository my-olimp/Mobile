package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationInfoState

class RegisterInfoViewModel(
    val context: Context
) : ViewModel() {


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
            is RegistrationEvent.OnSave -> {
                //TODO request
            }
        }
    }
}