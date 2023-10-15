package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.utils.State

data class LoginState (
    val email: String = "",
    val password: String = "",

    val isLogging: Boolean = false,

    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,

    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
) : State<LoginState> {

    override val tag: String
        get() = "LoginState"
    override fun onError(): LoginState {
        return this.copy(isError = true)
    }

    override fun onLoaderUpdate(value: Boolean): LoginState {
        return this.copy(isLoading = value)
    }

    override fun onNetworkError(): LoginState {
        return this.copy(isNetworkError = true)
    }

}
