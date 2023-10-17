package ramble.sokol.myolimp.feature_library.domain.states

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.utils.State

data class ChapterState(
    val subject: String? = null,
    val searchQuery: String? = null,

    val currentPage: Int = 1,
    val amountOfPages: Int = 1,

    val articles: List<ArticleModel> = emptyList(),

    val isLoading: Boolean = false,
    val isNetworkError: Boolean = false,
    val isError: Boolean = false,
    val isShowingFavourites: Boolean = false,
) : State<ChapterState> {
    override val tag: String
        get() = "ChapterState"

    override fun onError(): ChapterState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): ChapterState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): ChapterState {
        return this.copy(isLoading = value)
    }

}
