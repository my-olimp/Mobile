package ramble.sokol.myolimp.feature_library.presenation.mainScreen

sealed interface LibraryEvent {
    data object OnEmptyQuery : LibraryEvent
    data class OnSearchQueryUpdated(val query: String): LibraryEvent
    data class OnShowFavourites(val isShowing: Boolean): LibraryEvent
    data class OnCheckboxSubject(val subjectName: String): LibraryEvent
    data object OnFilterSubjectFromBottomSheet: LibraryEvent
}
