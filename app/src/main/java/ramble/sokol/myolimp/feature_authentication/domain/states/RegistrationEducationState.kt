package ramble.sokol.myolimp.feature_authentication.domain.states

data class RegistrationEducationState(
    val region: String = "",
    val city: String = "",
    val school: String = "",
    val grade: String = "",
    var regionError: Boolean = false,
    var cityError: Boolean = false,
    var schoolError: Boolean = false,
    var gradeError: Boolean = false
)
