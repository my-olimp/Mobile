package ramble.sokol.myolimp.feature_library.presenation

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class LibraryState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val isShowingFavourites: Boolean = false,
    val isShownFilterBottomSheet: Boolean = false,
    val isFilterActive: Boolean = false,
    val articles: List<ArticleModel> = emptyList(),
    val userSubjects: List<String> = emptyList()
)
