package ramble.sokol.myolimp.feature_splash_onBoarding.domain.states

import ramble.sokol.myolimp.utils.State

data class SplashState (
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
) : State<SplashState> {
    override val tag: String
        get() = "ViewModelSplash"

    override fun onError(): SplashState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): SplashState {
        TODO("Not yet implemented")
    }

    override fun onLoaderUpdate(value: Boolean): SplashState {
        TODO("Not yet implemented")
    }
}
