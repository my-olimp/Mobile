package ramble.sokol.myolimp.feature_library.domain.states

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class ArticleState(
    val article: ArticleModel = ArticleModel(),
    val answers: HashMap<Int, TaskState> = hashMapOf(),
    val status: Boolean = false, /* to recompose screen */
    val isLoading: Boolean = false
)
