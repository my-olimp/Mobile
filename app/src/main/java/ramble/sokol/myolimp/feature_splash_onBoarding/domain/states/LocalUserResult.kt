package ramble.sokol.myolimp.feature_splash_onBoarding.domain.states

sealed class LocalUserResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : LocalUserResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : LocalUserResult<T>(data, message)
    class Loading<T> : LocalUserResult<T>()
}
