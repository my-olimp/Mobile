package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.domain.events.LoginEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.LoginRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.LoginState
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions

class LoginViewModel : BaseViewModel<LoginState>(LoginState()) {

    private val repository = LoginRepository()

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
            }
            is LoginEvent.OnPasswordUpdated -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }

                checkAbilityLogging()
            }

            is LoginEvent.OnLogin -> {
                loginToAccount(
                    onSuccess = {

                        updateLoader(false)

                        event.navigator.navigate(
                            HomeScreenDestination()
                        ) {
                            popUpTo(NavGraphs.root) {
                                saveState = false
                            }
                            launchSingleTop = false
                            restoreState = false
                        }
                    },
                    onError = {
                        _state.update {
                            it.copy(
                                isPasswordError = true,
                                isEmailError = true,
                                isLoading = false
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
        updateLoader(true)

        if(isDataValid()) {
            launchIO {
                repository.login(
                    RequestLoginModel(
                        email = state.value.email,
                        password = state.value.password,
                    ),
                    onResult = {

                        // all correct
                        if (it?.code != null) {

                            // save token in data store
                            saveData(
                                it.code,
                                it.user
                            )

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
                        castError(ViewModelExceptions.Network)
                        Log.i(TAG, "Error sending request - $it")

                    }
                )
            }
        }
    }

    private fun isDataValid(): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
            _state.update {
                it.copy(
                    isEmailError = true,
                    isLogging = false
                )
            }

            return false
        }

        if (state.value.password.isEmpty()) {
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
                isLogging = android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches() && state.value.password.isNotEmpty()
            )
        }
    }

    private fun saveData(
        token: String,
        user: LocalUserModel
    ) {
        runBlocking {
            try {
                datastore.setToken(
                    key = CodeDataStore.ACCESS_TOKEN,
                    value = token
                )
    
                Log.i(TAG, "code - ${datastore.getToken(CodeDataStore.ACCESS_TOKEN).first()}")

                userRepository.deleteUsers()

                userRepository.saveUser(user)

                Log.i(TAG, "user - ${userRepository.getUser().firstOrNull()}")

            } catch (ex : Exception) {

                castError()

                Log.i(TAG, "exception - ${ex.message}")
            }
        }
    }

}
