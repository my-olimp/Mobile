package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.RegisterInfoScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSendingEmailModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSignUpModel
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SignUpRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SignUpState
import ramble.sokol.myolimp.feature_authentication.domain.utils.onlyLetters
import ramble.sokol.myolimp.feature_authentication.domain.utils.onlyNumbers
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

class SignUpViewModel : ViewModel(), KoinComponent {
    companion object {
        private const val TAG = "ViewModelSignUp"
    }

    private val context by inject<Context>()

    private val repository = SignUpRepository()

    private var database : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = database)


    private val _state = MutableStateFlow(
        SignUpState()
    )
    val state = _state.asStateFlow()

    private val dataStore = CodeDataStore()

    fun onEvent(
        event: SignUpEvent
    ) {
        when (event) {
            is SignUpEvent.OnSignUp -> {

                if (checkPasswordCorrectness())

                sendVerificationCode(
                  onError = {
                      showSnackbar(context.getString(R.string.register_auth_error_message))
                  },
                  onSent = { code->
                      showSnackbar(context.getString(R.string.success_send_code_message))

                      repository.signUp(
                          data = RequestSignUpModel(
                              code = code,
                              email = state.value.email,
                              firstName = "",
                              secondName = "",
                              thirdName = "",
                              password = state.value.password,
                          ),
                          onResult = {
                              Log.i(TAG, "completed - $it")

                              if (it != null) {

                                  // save token in data store
                                  saveData(
                                      it.code,
                                      it.user
                                  )

                                  // load user

                                  showSnackbar(context.getString(R.string.success_register_message))

                                  event.navigator.navigate(
                                      RegisterInfoScreenDestination()
                                  ) {
                                      popUpTo(NavGraphs.root) {
                                          saveState = false
                                      }
                                      launchSingleTop = false
                                      restoreState = false
                                  }

                              } else {
                                  showSnackbar("Не получилось создать аккаунт")
                              }
                          },
                          onError = {
                              Log.i(TAG, "error - $it")
                          }
                      )
                    }
                )
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

            is SignUpEvent.OnCode1Updated -> {
                _state.update {
                    it.copy(
                        code1 = event.code1,
                    )
                }
                isSendingCode(event.navigator)
            }
            is SignUpEvent.OnCode2Updated -> {
                _state.update {
                    it.copy(
                        code2 = event.code2,
                    )
                }
                isSendingCode(event.navigator)
            }
            is SignUpEvent.OnCode3Updated -> {
                _state.update {
                    it.copy(
                        code3 = event.code3,
                    )
                }
                isSendingCode(event.navigator)
            }
            is SignUpEvent.OnCode4Updated -> {
                _state.update {
                    it.copy(
                        code4 = event.code4,
                    )
                }
                isSendingCode(event.navigator)
            }
            is SignUpEvent.OnCode5Updated -> {
                _state.update {
                    it.copy(
                        code5 = event.code5,
                    )
                }
                isSendingCode(event.navigator)
            }
            is SignUpEvent.OnClosedSnackBar -> hideSnackbar()
            is SignUpEvent.OnCode6Updated -> {
                _state.update {
                    it.copy(
                        code6 = event.code6,
                    )
                }
                isSendingCode(event.navigator)
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

    private fun signUp (
        navigator: DestinationsNavigator,
        onError: () -> Unit
    ) {
        val code = _state.value.code1.toString() + _state.value.code2.toString() + _state.value.code3.toString() + _state.value.code4.toString() + _state.value.code5.toString() + _state.value.code6.toString()

        Log.i(TAG, "user code - $code")

        repository.signUp(
            data = RequestSignUpModel(
                code = code,
                email = _state.value.email,
                firstName = _state.value.email,
                secondName = _state.value.email,
                thirdName = _state.value.email,
                password = _state.value.password,
            ),
            onResult = {
                Log.i(TAG, "completed - $it")

                if (it != null) {

                    // save token in data store
                    saveData(
                        it.code,
                        it.user
                    )

                    // load user

                    showSnackbar(context.getString(R.string.success_register_message))

                    navigator.navigate(
                        RegisterInfoScreenDestination()
                    ) {
                        popUpTo(NavGraphs.root) {
                            saveState = false
                        }
                        launchSingleTop = false
                        restoreState = false
                    }

                } else {
                    onError()
                }
           },
            onError = {
                Log.i(TAG, "error - $it")
                onError()
            }
        )
    }

    private fun isSendingCode(
        navigator: DestinationsNavigator,
    ) {
        if (
            _state.value.code1.toString().isNotEmpty() && _state.value.code1 != null
            && _state.value.code2.toString().isNotEmpty() && _state.value.code2 != null
            && _state.value.code3.toString().isNotEmpty() && _state.value.code3 != null
            && _state.value.code4.toString().isNotEmpty() && _state.value.code4 != null
            && _state.value.code5.toString().isNotEmpty() && _state.value.code5 != null
            && _state.value.code6.toString().isNotEmpty() && _state.value.code6 != null
        ) {
            signUp(
                navigator = navigator
            ) {
                _state.update {
                    it.copy(
                        isCodeError = true,
                    )
                }
            }
        }
    }

    private fun checkPasswordCorrectness() : Boolean {

        val password = _state.value.password

        if (password.contains(" ")) {
            _state.update {
                it.copy(
                    passwordError = context.getString(R.string.error_password_spaces),
                )
            }
            return false
        } else if (!password.matches("^[a-zA-Z0-9!@#$%^&*]*$".toRegex())) {
            _state.update {
                it.copy(
                    passwordError = context.getString(R.string.error_password_letters),
                )
            }
            return false
        }

        return true
    }


    private fun sendVerificationCode(
        onSent: (String) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            repository.sendVerificationCode(
                RequestSendingEmailModel(
                    email = _state.value.email
                ),
                onResult = {
                    if (it != null) {
                        onSent(it)
                    } else {
                        _state.update { state->
                            state.copy(
                                isEmailError = true,
                            )
                        }
                    }
                },
                onError = {
                    onError()
                    Log.i(TAG, "error - ${it.message}")
                }
            )
        }
    }

    private fun saveData(
        token: String,
        user: LocalUserModel
    ) {
        runBlocking {
            dataStore.setToken(
                key = ACCESS_TOKEN,
                value = token
            )

            Log.i(TAG, "code - ${dataStore.getToken(ACCESS_TOKEN).first()}")

            userRepository.deleteUsers()

            userRepository.saveUser(user)
        }
    }

    private fun showSnackbar(
        text: String
    ) {
        _state.update {
            it.copy(
                isShowingSnackbar = true,
                snackbarText = text
            )
        }
    }

    private fun hideSnackbar() {
        _state.update {
            it.copy(
                isShowingSnackbar = false,
                snackbarText = null
            )
        }
    }

}
