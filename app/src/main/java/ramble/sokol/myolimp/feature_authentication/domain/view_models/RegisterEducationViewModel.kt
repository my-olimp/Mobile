package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationEducationState

class RegisterEducationViewModel(
    val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationEducationState())
    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationEvent
    ) {
        when(event) {
            is RegistrationEvent.OnGradeChanged -> {
                _state.update {
                    it.copy(
                        grade = event.grade
                    )
                }
            }
            is RegistrationEvent.OnCityChanged -> {
                _state.update {
                    it.copy(
                        city = event.city
                    )
                }
            }
            is RegistrationEvent.OnRegionChanged -> {
                _state.update {
                    it.copy(
                        region = event.region
                    )
                }
            }
            is RegistrationEvent.OnSchoolChanged -> {
                _state.update {
                    it.copy(
                        school = event.school
                    )
                }
            }
            is RegistrationEvent.OnNext -> {

            }
            else -> {}
         }
    }

}