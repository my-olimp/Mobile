package ramble.sokol.myolimp.feature_library.domain.states

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.utils.State

data class ArticleState(
    val article: ArticleModel = ArticleModel(),
    val answers: HashMap<Int, TaskState> = hashMapOf(),
    val status: Boolean = false, /* to recompose screen */
    val isLoading: Boolean = false,
    val isNetworkError: Boolean = false,
    val isError: Boolean = false
) : State<ArticleState> {
    override val tag: String
        get() = "ArticleState"

    override fun onError(): ArticleState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): ArticleState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): ArticleState {
        return this.copy(isLoading = value)
    }

}
