package ramble.sokol.myolimp.feature_library.domain.events

import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryEvent

sealed interface ChapterEvent {
    data object OnEmptyQuery : ChapterEvent
    data class OnSearchQueryUpdated(val query: String): ChapterEvent
    data class OnShowFavourites(val isShowing: Boolean): ChapterEvent
    data class OnSearchArticlesBySubject(val subject: String) : ChapterEvent
}
