package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.feature_authentication.data.models.Region

data class RegistrationEducationState(
    val region: Region = Region(),
    val city: String = "",
    val school: String = "",
    val grade: String = "",
    var regionError: Boolean = false,
    var cityError: Boolean = false,
    var schoolError: Boolean = false,
    var gradeError: Boolean = false,
    val regionList: List<Region> = listOf(),
    val cityList: List<String> = listOf()
)
