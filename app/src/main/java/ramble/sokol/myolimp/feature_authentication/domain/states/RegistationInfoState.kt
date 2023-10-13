package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.utils.State

data class RegistrationInfoState(
    val isLoading: Boolean = false,
    val fio: String = "",
    val gender: String = "f",
    val bdate: String = "",

    val activityType: String = "",
    val requestActivityType: String = "",

    val fioError: Boolean = false,
    val bdateError: Boolean = false,
    val activityTypeError: Boolean = false,

    val isError: Boolean = false
) : State<RegistrationInfoState> {
    override fun onError(): RegistrationInfoState {
        return this.copy(isError = true)
    }

    override fun onLoaderUpdate(value: Boolean): RegistrationInfoState {
        return this.copy(isLoading = value)
    }
}