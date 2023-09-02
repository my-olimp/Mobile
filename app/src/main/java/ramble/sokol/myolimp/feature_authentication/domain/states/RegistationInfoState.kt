package ramble.sokol.myolimp.feature_authentication.domain.states

data class RegistrationInfoState(
    val fio: String = "",
    val gender: String = "",
    val bdate: String = "",
    val activityType: String = ""
)