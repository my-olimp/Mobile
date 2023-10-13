package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.domain.events.LoginEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.LoginRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.LoginState
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

class LoginViewModel : ViewModel(), KoinComponent {
    companion object {
        const val TAG = "ViewModelLogin"
    }

    private val repository = LoginRepository()

    private val context by inject<Context>()
    private var database : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = database)

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

                        _state.update { state->
                            state.copy(
                                isLoading = false
                            )
                        }

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
        _state.update {
            it.copy(
                isLoading = true
            )
        }

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

                            Log.i(TAG, "Error sending request - $it")

                            onError()
                        }
                    )

                } catch (ex: Exception) {
                    // if user not found

                    Log.i(TAG, "exception - ${ex.message}")

                    _state.update {
                        it.copy(
                            isError = true
                        )
                    }

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

    private fun saveData(
        token: String,
        user: LocalUserModel
    ) {
        runBlocking {
            try {
                dataStore.setToken(
                    key = CodeDataStore.ACCESS_TOKEN,
                    value = token
                )
    
                Log.i(TAG, "code - ${dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()}")

                userRepository.deleteUsers()

                userRepository.saveUser(user)

                Log.i(TAG, "user - ${userRepository.getUser().firstOrNull()}")

            } catch (ex : Exception) {

                _state.update {
                    it.copy(
                        isError = true
                    )
                }

                Log.i(TAG, "exception - ${ex.message}")
            }
        }
    }

}
