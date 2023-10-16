package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.utils.State

data class SendCodeState(
    val email: String = "",
    val emailError: Boolean = false,
    val timer: Int = 60,

    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isLoading: Boolean = false
) : State<SendCodeState> {
    override val tag: String
        get() = "SendCodeState"

    override fun onError(): SendCodeState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): SendCodeState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): SendCodeState {
        return this.copy(isLoading = value)
    }

}