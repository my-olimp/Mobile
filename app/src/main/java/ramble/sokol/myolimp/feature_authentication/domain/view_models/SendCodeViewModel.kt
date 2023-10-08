package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.SendCodeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestForgotPasswordModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestRegisterEmailModel
import ramble.sokol.myolimp.feature_authentication.domain.events.SendCodeEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SendCodeRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SendCodeState
import ramble.sokol.myolimp.utils.enums.ResponseCode

class SendCodeViewModel : ViewModel() {

    companion object {
        const val TAG = "ForgotPasswordViewModel"
    }

    private val _state = MutableStateFlow(SendCodeState())
    val state = _state.asStateFlow()

    private val repository = SendCodeRepository()

    fun onEvent(event: SendCodeEvent) {
        when(event) {
            is SendCodeEvent.OnEmailTyped -> {
                _state.update {
                    it.copy(email = event.email,emailError = false)
                }
            }
            is SendCodeEvent.OnContinue -> {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
                    sendRequest(navigator = event.navigator , resendEmail = null)
                } else _state.update { it.copy(emailError = true) }
            }
            is SendCodeEvent.OnResendCode -> {
                sendRequest(resendEmail = event.email, navigator = event.navigator, isRegistering = event.isRegistering)
            }
        }
    }

    private fun sendRequest(navigator: DestinationsNavigator, resendEmail: String?, isRegistering: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if(isRegistering) {
                    repository.sendRegisterEmail(
                        body = RequestRegisterEmailModel(resendEmail ?: state.value.email),
                        onResult = { code ->
                            Log.i(TAG, "response register email code is $code")
                            when (code) {
                                ResponseCode.FORBIDDEN.code -> {
                                    Log.i(TAG, "forbidden request register email")
                                }

                                ResponseCode.SUCCESS.code -> {
                                    Log.i(
                                        TAG,
                                        "success response on register email ${resendEmail ?: state.value.email}"
                                    )
                                    _state.update {
                                        it.copy(
                                            updatedTimer = !state.value.updatedTimer,
                                            timer = 60
                                        )
                                    }
                                }
                            }
                        },
                        onError = {
                            Log.i(TAG, "register email throwed ${it.message}")
                        }
                    )
                } else {
                    repository.sendForgotEmail(
                        body = RequestForgotPasswordModel(resendEmail ?: state.value.email),
                        onResult = { code ->
                            Log.i(TAG, "response code - $code")
                            if (code == ResponseCode.SUCCESS.code) {
                                if (resendEmail.isNullOrEmpty()) {
                                    navigator.navigate(SendCodeScreenDestination(email = state.value.email))
                                } else {
                                    Log.i(TAG, "[resend email code]")
                                    _state.update {
                                        it.copy(
                                            updatedTimer = !state.value.updatedTimer,
                                            timer = 60
                                        )
                                    }
                                }
                            } else _state.update { state -> state.copy(emailError = true) }
                        },
                        onError = {
                            Log.i(TAG, "forgot password throwed ${it.message}")
                            _state.update { state -> state.copy(emailError = true) }
                        }
                    )
                }
            } catch (e: Exception) {
                Log.i(TAG,"forgot password throwed - ${e.message}")
            }
        }
    }
}