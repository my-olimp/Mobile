package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.destinations.SendCodeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestForgotPasswordModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestRegisterEmailModel
import ramble.sokol.myolimp.feature_authentication.domain.events.SendCodeEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SendCodeRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SendCodeState
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.enums.ResponseCode
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions

class SendCodeViewModel : BaseViewModel<SendCodeState>(SendCodeState()) {

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
            is SendCodeEvent.OnUpdateTimer -> {
                _state.update { it.copy(timer = state.value.timer.minus(1)) }
            }
        }
    }

    private fun sendRequest(navigator: DestinationsNavigator, resendEmail: String?, isRegistering: Boolean = false) {
        updateLoader(true)
        launchIO {
            if (isRegistering) {
                repository.sendRegisterEmail(
                    body = RequestRegisterEmailModel(resendEmail ?: state.value.email),
                    onResult = { code ->
                        Log.i(TAG, "response register email code is $code")
                        when (code) {
                            ResponseCode.FORBIDDEN.code -> {
                                Log.i(TAG, "forbidden request register email")
                                _state.update { it.copy(emailError = true) }
                            }

                            ResponseCode.SUCCESS.code -> {
                                Log.i(
                                    TAG,
                                    "success response on register email ${resendEmail ?: state.value.email}"
                                )
                                _state.update {
                                    it.copy(timer = 60)
                                }
                            }
                        }
                        updateLoader(false)
                    },
                    onError = {
                        Log.i(TAG, "register email throwed ${it.message}")
                        castError(ViewModelExceptions.Network)
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
                                    it.copy(timer = 60)
                                }
                            }
                        } else _state.update { state -> state.copy(emailError = true) }
                        updateLoader(false)
                    },
                    onError = {
                        Log.i(TAG, "forgot password throwed ${it.message}")
                        castError(ViewModelExceptions.Network)
                    }
                )
            }
        }
    }
}