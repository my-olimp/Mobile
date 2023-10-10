package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

data class ProfileState (
    val user: LocalUserModel = LocalUserModel(),

    val id: String? = null,
    val firstName: String? = null,
    val secondName: String? = null,
    val thirdName: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val snils: String? = null,
    val region: Region? = null,
    val city: City? = null,
    val school: School? = null,
    val phone: String? = null,
    val email: String? = null,
    val grade: Int? = null,
    val hasThird: Boolean = true,
    val accountType: String? = null,

    val isLoaded: Boolean = false,

    val firstNameError: Boolean = false,
    val secondNameError: Boolean = false,
    val thirdNameError: Boolean = false,
    val dobError: Boolean = false,
    val snilsError: Boolean = false
)
