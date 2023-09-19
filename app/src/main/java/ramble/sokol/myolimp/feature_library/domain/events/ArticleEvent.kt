package ramble.sokol.myolimp.feature_library.domain.events

sealed interface ArticleEvent {
    data class OnCheckAnswer(val taskNum: Int,val taskId: Int, val answer: String, val blockId: Int): ArticleEvent

    data class OnAnswerTyped(val taskId: Int,val taskNum: Int,val answer: String,val blockId: Int): ArticleEvent

}