package ramble.sokol.myolimp.feature_library.domain.states

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class ChapterState(
    val subject: String? = null,
    val searchQuery: String? = null,

    val currentPage: Int = 1,
    val amountOfPages: Int = 1,

    val articles: List<ArticleModel> = emptyList(),

    val isLoading: Boolean = false,
    val isShowingFavourites: Boolean = false,
)
