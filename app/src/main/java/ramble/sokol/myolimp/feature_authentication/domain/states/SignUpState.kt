package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.utils.State

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
    val passwordError: String? = null,

    val isRegistering: Boolean = false,
    val isEmailError: Boolean = false,
    val isCodeError: Boolean = false,

    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val isNetworkError: Boolean = false,

    val isShowingSnackbar: Boolean = false,
    val snackbarText: String? = null,

    val code1: Int? = null,
    val code2: Int? = null,
    val code3: Int? = null,
    val code4: Int? = null,
    val code5: Int? = null,
    val code6: Int? = null,

    val passwordStatus: Float = 0f,
) : State<SignUpState> {
    override val tag: String
        get() = "SignUpState"

    override fun onError(): SignUpState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): SignUpState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): SignUpState {
        return this.copy(isLoading = value)
    }

}