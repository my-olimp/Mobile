package ramble.sokol.myolimp.feature_authentication.domain.states

data class RegistrationInfoState(
    val isLoading: Boolean = false,
    val fio: String = "",
    val gender: String = "f",
    val bdate: String = "",

    val activityType: String = "",
    val requestActivityType: String = "",

    var fioError: Boolean = false,
    var bdateError: Boolean = false,
    var activityTypeError: Boolean = false
)