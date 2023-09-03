package ramble.sokol.myolimp.feature_authentication.domain.states

data class RegistrationInfoState(
    val fio: String = "",
    val gender: String = "f",
    val bdate: String = "",
    val activityType: String = "",
    var fioError: Boolean = false
)