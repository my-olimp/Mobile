package ramble.sokol.myolimp.feature_profile.domain.states

data class ProfilePersonalState(
    val firstName: String? = null,
    val secondName: String? = null,
    val thirdName: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val snils: String? = null,
    val hasThird: Boolean = true,


    val firstNameError: Boolean = false,
    val secondNameError: Boolean = false,
    val thirdNameError: Boolean = false,
    val dobError: Boolean = false,
    val snilsError: Boolean = false
)
