package ramble.sokol.myolimp.feature_authentication.domain.states

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
    val passwordError: String? = null,

    val isRegistering: Boolean = false,
    val isEmailError: Boolean = false,
    val isCodeError: Boolean = false,

    val isShowingSnackbar: Boolean = false,
    val snackbarText: String? = null,

    val code1: Int? = null,
    val code2: Int? = null,
    val code3: Int? = null,
    val code4: Int? = null,
    val code5: Int? = null,
    val code6: Int? = null,

    val passwordStatus: Float = 0f,
)