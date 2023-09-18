package ramble.sokol.myolimp.feature_library.domain.events

sealed interface ArticleEvent {
    data class OnCheckAnswer(val taskNum: Int,val taskId: Int, val answer: String, val blockId: Int): ArticleEvent
}