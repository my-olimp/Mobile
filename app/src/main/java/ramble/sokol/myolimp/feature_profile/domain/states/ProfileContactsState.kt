package ramble.sokol.myolimp.feature_profile.domain.states

data class ProfileContactsState(
    val email: String? = null,
    val phone: String? = null,

    val emailError: Boolean = false,
    val phoneError: Boolean = false
)
