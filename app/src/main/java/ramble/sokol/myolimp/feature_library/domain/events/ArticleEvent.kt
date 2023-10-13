package ramble.sokol.myolimp.feature_library.domain.events

sealed interface ArticleEvent {
    data class OnFetchArticle(val id: Int): ArticleEvent

    data class OnFavourites(val isAdding: Boolean): ArticleEvent
    data class OnCheckAnswer(val taskId: Int, val blockId: Int): ArticleEvent
    data class OnAnswerTyped(val taskId: Int,val taskNum: Int,val answer: String,val blockId: Int): ArticleEvent
}
