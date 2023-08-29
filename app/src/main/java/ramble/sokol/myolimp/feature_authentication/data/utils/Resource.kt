package ramble.sokol.myolimp.feature_authentication.data.utils

sealed class Resource<T> (
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(data: T? = null, message: String?) : Resource<T>(data, message)

    class Loading<T> : Resource<T>()

}
