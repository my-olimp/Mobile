package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSendingEmailModel
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SignUpRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SignUpState

class SignUpViewModel (
    val context: Context
) : ViewModel() {
    companion object {
        private const val TAG = "ViewModelSignUp"
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
                  sendVerificationCode(
                      onError = {
                          Log.i(TAG, "error")

                          Toast.makeText(context,
                              context.getString(R.string.register_auth_error_message), Toast.LENGTH_SHORT).show()
                      },
                      onSent = {
                          Log.i(TAG, "sent")

                          Toast.makeText(context,
                              context.getString(R.string.success_send_code_message), Toast.LENGTH_SHORT).show()
                      }
                  )
            }
            is SignUpEvent.OnSentToken -> {

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

    private fun sendVerificationCode(
        onSent: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.sendVerificationCode(
                    RequestSendingEmailModel(
                        email = _state.value.email
                    ),
                    onResult = {
                        onSent()
                        Log.i(TAG, "success - $it")
                    },
                    onError = {
                        onError()
                        Log.i(TAG, "error - ${it.message}")
                    }
                )
            } catch (ex: Exception) {
                Log.i(TAG, "exception - ${ex.message}")
            }
        }
    }
}
