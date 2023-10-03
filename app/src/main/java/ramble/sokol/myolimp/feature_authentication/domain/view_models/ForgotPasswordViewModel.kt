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
import ramble.sokol.myolimp.feature_authentication.domain.events.ForgotPasswordEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.ForgotPasswordRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.ForgotPasswordState
import ramble.sokol.myolimp.utils.enums.ResponseCode

class ForgotPasswordViewModel : ViewModel() {

    companion object {
        const val TAG = "ForgotPasswordViewModel"
    }

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    private val repository = ForgotPasswordRepository()

    fun onEvent(event: ForgotPasswordEvent) {
        when(event) {
            is ForgotPasswordEvent.OnEmailTyped -> {
                _state.update {
                    it.copy(email = event.email,emailError = false)
                }
            }
            is ForgotPasswordEvent.OnContinue -> {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
                    sendRequest(navigator = event.navigator)
                } else _state.update { it.copy(emailError = true) }
            }
        }
    }

    private fun sendRequest(navigator: DestinationsNavigator) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.sendEmail(
                    body = RequestForgotPasswordModel(state.value.email),
                    onResult = { code ->
                        Log.i(TAG,"response code - $code")
                        if(code == ResponseCode.SUCCESS.code) {
                            navigator.navigate(SendCodeScreenDestination)
                        } else throw Exception("another code: $code")
                    },
                    onError = { throwable ->
                        throw throwable
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG,"forgot password throwed - ${e.message}")
            }
        }
    }

}