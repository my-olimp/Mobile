package ramble.sokol.myolimp.feature_library.domain.events

sealed interface LibraryEvent {
    data class OnSearchQueryUpdated(
        val query: String
    ): LibraryEvent
    data class OnShowFavourites(
        val isShowing: Boolean
    ): LibraryEvent
}
