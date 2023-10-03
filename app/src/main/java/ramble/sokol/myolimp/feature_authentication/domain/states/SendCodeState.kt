package ramble.sokol.myolimp.feature_authentication.domain.states

data class SendCodeState(
    val email: String = "",
    val emailError: Boolean = false,
    val updatedTimer: Boolean = false,
    val timer: Int = 60,
)