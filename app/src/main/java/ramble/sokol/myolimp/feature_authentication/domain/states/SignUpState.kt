package ramble.sokol.myolimp.feature_authentication.domain.states

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",

    val isRegistering: Boolean = false,

    val passwordStatus: Float = 0f,
)