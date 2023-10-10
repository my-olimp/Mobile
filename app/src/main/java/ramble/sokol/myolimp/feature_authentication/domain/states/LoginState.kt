package ramble.sokol.myolimp.feature_authentication.domain.states

data class LoginState (
    val email: String = "",
    val password: String = "",

    val isLogging: Boolean = false,

    val isLoading: Boolean = false,
    val isError: Boolean = false,

    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
)
