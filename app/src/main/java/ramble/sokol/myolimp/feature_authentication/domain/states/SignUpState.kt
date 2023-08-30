package ramble.sokol.myolimp.feature_authentication.domain.states

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",

    val isRegistering: Boolean = false,

    val code1: Int? = null,
    val code2: Int? = null,
    val code3: Int? = null,
    val code4: Int? = null,
    val code5: Int? = null,
    val code6: Int? = null,

    val passwordStatus: Float = 0f,
)