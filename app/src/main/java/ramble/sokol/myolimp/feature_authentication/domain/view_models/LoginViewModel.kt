package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.domain.events.LoginEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.LoginRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.LoginState
import ramble.sokol.myolimp.feature_profile.data.Constants

class LoginViewModel : ViewModel() {
    companion object {
        const val TAG = "ViewModelLogin"
    }

    private val repository = LoginRepository()

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(
        LoginState()
    )

    val state = _state.asStateFlow()

    fun onEvent(
        event: LoginEvent
    ) {
        when (event) {
            is LoginEvent.OnEmailUpdated -> {
                _state.update {
                    it.copy(
                        email = event.email,
                    )
                }

                checkAbilityLogging()

                Log.i(TAG, "updated email - ${_state.value.email}")

            }
            is LoginEvent.OnPasswordUpdated -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }

                checkAbilityLogging()

                Log.i(TAG, "updated password - ${_state.value.password}")
            }

            is LoginEvent.OnLogin -> {
                loginToAccount(
                    onSuccess = {

                        // TODO: Make SnackBar

//                        Toast.makeText(context,
//                            context.getString(R.string.success_autherization_message), Toast.LENGTH_SHORT).show()

                        event.navigator.navigate(HomeScreenDestination)
                    },
                    onError = {
                        _state.update {
                            it.copy(
                                isPasswordError = true,
                                isEmailError = true
                            )
                        }
                    }
                ) 
            }
        }
    }

    private fun loginToAccount(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        if(isDataValid()) {
            viewModelScope.launch {
                try {

                    repository.login(
                        RequestLoginModel(
                            email = _state.value.email,
                            password = _state.value.password,
                        ),
                        onResult = {
                            // all correct
                            if (it?.code != null) {
                                
                                // save token in data store
                                saveToken(it.code)

                                // navigate to main screen
                                onSuccess()

                            // invalid data
                            } else {

                                Log.i(TAG, "Error getting code")

                                onError()
                            }
                        },
                        onError = {
                            // can not get data

                            Log.i(TAG, "Error sending request - $it")

                            onError()
                        }
                    )

                } catch (ex: Exception) {
                    // if user not found

                    Log.i(TAG, "exception - ${ex.message}")

                    onError()
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()) {
            _state.update {
                it.copy(
                    isEmailError = true,
                    isLogging = false
                )
            }

            return false
        }

        if (_state.value.password.isEmpty()) {
            _state.update {
                it.copy(
                    isPasswordError = true,
                    isLogging = false
                )
            }

            return false
        }

        return true
    }

    private fun checkAbilityLogging() {
        _state.update {
            it.copy(
                isLogging = android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches() && _state.value.password.isNotEmpty()
            )
        }
    }

    private fun saveToken(
        token: String
    ) {
        viewModelScope.launch {
            dataStore.setToken(
                key = Constants.ACCESS_TOKEN,
                value = token
            )
        }
    }

}
