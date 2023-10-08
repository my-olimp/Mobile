package ramble.sokol.myolimp.feature_library.domain.events

sealed interface ChapterEvent {
    data class OnSearchArticlesBySubject(val subject: String) : ChapterEvent
}
