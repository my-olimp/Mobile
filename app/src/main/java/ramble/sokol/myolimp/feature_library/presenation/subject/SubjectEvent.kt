package ramble.sokol.myolimp.feature_library.presenation.subject

sealed interface SubjectEvent {
    data class OnLoadSubjectContent(val subjectID: Long): SubjectEvent
    data class OnSearchQueryUpdated(val query: String): SubjectEvent
    data class OnShowFavourites(val isShowing: Boolean): SubjectEvent
    data object OnFilterFromBottomSheet: SubjectEvent
}
