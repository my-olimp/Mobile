package ramble.sokol.myolimp.utils

interface State<T> {

    val tag: String

    fun onError() : T

    fun onNetworkError(): T

    fun onLoaderUpdate(value: Boolean) : T

}