package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.RegisterInfoScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSendingEmailModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSignUpModel
import ramble.sokol.myolimp.feature_authentication.domain.events.SignUpEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_authentication.domain.repositories.SignUpRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.SignUpState
import ramble.sokol.myolimp.feature_authentication.domain.utils.onlyLetters
import ramble.sokol.myolimp.feature_authentication.domain.utils.onlyNumbers
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions

class SignUpViewModel : BaseViewModel<SignUpState>(SignUpState()) {

    private val repository = SignUpRepository()

    fun onEvent(
        event: SignUpEvent
    ) {
        when (event) {
            is SignUpEvent.OnSignUp -> {

                if (isDataValid()) {

                    sendVerificationCode(
                        onError = {
                            castError(ViewModelExceptions.Network)
                        },
                        onSent = { code ->
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
                                    castError(ViewModelExceptions.Network)
                                }
                            )
                        }
                    )
                }
            }
            is SignUpEvent.OnEmailUpdated -> {
                _state.update {
                    it.copy(
                        email = event.email,
                        isEmailError = false
                    )
                }
                checkAbilityRegistering()
            }
            is SignUpEvent.OnPasswordUpdated -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        isDiffPassword = false,
                        isBadPassword = false
                    )
                }

                setStatus()
                checkAbilityRegistering()
            }
            is SignUpEvent.OnConfirmedPasswordUpdated -> {
                _state.update {
                    it.copy(
                        confirmedPassword = event.confirmedPassword,
                        isDiffPassword = false,
                        isBadPassword = false
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

        val password = state.value.password

        val status =
            if (password.isEmpty()) 0f
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

    private fun isDataValid() : Boolean {
        var isValid = true

        if(state.value.email.isEmpty()) {
            _state.update {
                it.copy(
                    isEmailError = true,
                    emailError = context.getString(R.string.empty_email_error)
                )
            }
            isValid = false
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
            _state.update {
                it.copy(
                    isEmailError = true,
                    emailError = context.getString(R.string.bad_pattern_email_error)
                )
            }
            isValid = false
        }
        if(!checkPasswordCorrectness()) isValid = false
        return isValid
    }

    private fun checkPasswordCorrectness() : Boolean {

        val password = state.value.password
        var isValid = true

        if(password.isEmpty()) {
            _state.update {
                it.copy(
                    passwordError = context.getString(R.string.empty_pass_error),
                    isBadPassword = true
                )
            }
            isValid = false
        }
        if (password.contains(" ")) {
            _state.update {
                it.copy(
                    passwordError = context.getString(R.string.error_password_spaces),
                    isBadPassword = true
                )
            }
            isValid = false
        } else if (!password.matches("^[a-zA-Z0-9!@#$%^&*]*$".toRegex())) {
            _state.update {
                it.copy(
                    passwordError = context.getString(R.string.error_password_letters),
                    isBadPassword = true
                )
            }
            isValid = false
        }
        if(password != state.value.confirmedPassword) {
            _state.update {
                it.copy(isDiffPassword = true)
            }
            isValid = false
        }

        return isValid
    }


    private fun sendVerificationCode(
        onSent: (String) -> Unit,
        onError: () -> Unit
    ) {
        updateLoader(true)
        launchIO {
            repository.sendVerificationCode(
                RequestSendingEmailModel(
                    email = state.value.email
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
                    updateLoader(false)
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
            datastore.setToken(
                key = ACCESS_TOKEN,
                value = token
            )

            Log.i(TAG, "code - ${datastore.getToken(ACCESS_TOKEN).first()}")

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
