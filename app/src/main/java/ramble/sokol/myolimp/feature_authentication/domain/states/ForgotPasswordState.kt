package ramble.sokol.myolimp.feature_authentication.domain.states

data class ForgotPasswordState(
    val email: String = "",
    val emailError: Boolean = false
)