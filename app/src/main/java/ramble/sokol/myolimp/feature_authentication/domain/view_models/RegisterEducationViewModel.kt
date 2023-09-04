package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationEducationState

class RegisterEducationViewModel(
    val context: Context
) : ViewModel() {

    val repository = RegistrationRepository(context)

    val dataStore = CodeDataStore(context)

    private val _state = MutableStateFlow(RegistrationEducationState())
    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationEvent
    ) {
        when(event) {
            is RegistrationEvent.OnGradeChanged -> {
                _state.update {
                    it.copy(
                        grade = event.grade,
                        gradeError = false
                    )
                }
            }
            is RegistrationEvent.OnCityChanged -> {
                _state.update {
                    it.copy(
                        city = event.city,
                        cityError = false
                    )
                }
            }
            is RegistrationEvent.OnRegionChanged -> {
                _state.update {
                    it.copy(
                        region = event.region,
                        regionError = false
                    )
                }
            }
            is RegistrationEvent.OnSchoolChanged -> {
                _state.update {
                    it.copy(
                        school = event.school,
                        schoolError = false
                    )
                }
            }
            is RegistrationEvent.OnNext -> {
                if(checkData()) {
                    Toast.makeText(context,"data is valid",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context,"data isnt valid",Toast.LENGTH_LONG).show()
                }
            }
            else -> {}
         }
    }

    private fun checkData(): Boolean {
        var isValid = true
        if (state.value.region == "") {
            _state.update { it.copy(regionError = true) }
            isValid = false
        }
        if (state.value.city == "") {
            _state.update { it.copy(cityError = true) }
            isValid = false
        }
        if (state.value.school == "") {
            _state.update { it.copy(schoolError = true) }
            isValid = false
        }
        if (state.value.grade == "") {
            _state.update { it.copy(gradeError = true) }
            isValid = false
        }
        return isValid
    }
}