package ramble.sokol.myolimp.utils.exceptions

sealed class ViewModelExceptions : Exception() {
    data object Network : ViewModelExceptions()
    data object DataValid: ViewModelExceptions()
}
