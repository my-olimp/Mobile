package ramble.sokol.myolimp.feature_library.presenation

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class LibraryState (
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val isShowingFavourites: Boolean = false,
    val articles: List<ArticleModel> = emptyList()
)
