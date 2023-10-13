package ramble.sokol.myolimp.utils

interface State<T> {
    fun onError() : T

    fun onLoaderUpdate(value: Boolean) : T

}