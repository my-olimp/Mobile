package ramble.sokol.myolimp.feature_library.presenation.subject

data class SubjectState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val isShowingFavourites: Boolean = false,
    val subjectName: String = ""
)
