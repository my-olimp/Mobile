package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SignUpRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SignUpState

class SignUpViewModel (
    context: Context
) : ViewModel() {
    companion object {
        const val TAG = "ViewModelSignUp"
    }

    private val repository = SignUpRepository()

    private val _state = MutableStateFlow(
        SignUpState()
    )

    val state = _state.asStateFlow()

    fun onEvent(
        event: SignUpEvent
    ) {
        when (event) {
            is SignUpEvent.OnSignUp -> {
                signUp(
                    navigator = event.navigator
                ) {
                  // TODO
                }
            }
            is SignUpEvent.OnEmailUpdated -> {
                _state.update {
                    it.copy(
                        email = event.email,
                    )
                }
                checkAbilityRegistering()
            }
            is SignUpEvent.OnPasswordUpdated -> {
                _state.update {
                    it.copy(
                        password = event.password,
                    )
                }

                setStatus()
                checkAbilityRegistering()
            }
            is SignUpEvent.OnConfirmedPasswordUpdated -> {
                _state.update {
                    it.copy(
                        confirmedPassword = event.confirmedPassword,
                    )
                }
                checkAbilityRegistering()
            }
        }
    }

    private fun checkAbilityRegistering() {
        _state.update {
            it.copy(
                isRegistering = android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches() && _state.value.password.isNotEmpty() && _state.value.confirmedPassword == _state.value.password
            )
        }
    }

    private fun setStatus() {

        val password = _state.value.password

        val status = if (password.isEmpty()) 0f
                else if (password.length in 1..7) 0.3f
                else if (password.onlyLetters() || password.onlyNumbers()) 0.6f
                else 1f

        _state.update {
            it.copy(
                passwordStatus = status,
            )
        }
    }

    private fun String.onlyLetters(): Boolean = (firstOrNull { !it.isLetter() } == null)

    private fun String.onlyNumbers(): Boolean = (firstOrNull { !it.isDigit() } == null)

    private fun signUp (
        navigator: DestinationsNavigator,
        onError: () -> Unit
    ) {

    }
}
