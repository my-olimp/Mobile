package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterImageState

class RegisterImageViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterImageState())
    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationImageEvent
    ) {
        when(event) {
            is RegistrationImageEvent.OnSnilsChanged -> {
                _state.update { value ->
                    value.copy(
                        snils = event.snils
                    )
                }
            }
            is RegistrationImageEvent.OnNext -> {
                viewModelScope.launch {

                }
            }
            is RegistrationImageEvent.OnImageChanged -> {
                _state.update { value ->
                    value.copy(
                        profileImg = event.uri
                    )
                }
            }
        }
    }
}

sealed interface RegistrationImageEvent {
    data class OnSnilsChanged(val snils: String): RegistrationImageEvent
    data class OnImageChanged(val uri: Uri?): RegistrationImageEvent
    data object OnNext: RegistrationImageEvent
}
