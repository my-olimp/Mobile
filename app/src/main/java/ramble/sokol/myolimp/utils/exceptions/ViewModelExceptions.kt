package ramble.sokol.myolimp.utils.exceptions

sealed interface ViewModelExceptions {
    data object Network : ViewModelExceptions
    data object DataValid: ViewModelExceptions
}
