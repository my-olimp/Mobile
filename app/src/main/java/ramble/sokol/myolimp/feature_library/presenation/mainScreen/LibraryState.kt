package ramble.sokol.myolimp.feature_library.presenation.mainScreen

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class LibraryState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isShowingFavourites: Boolean = false,

    val searchQuery: String = "",

    val currentPage: Int = 1,
    val amountOfPages: Int = 1,

    val articles: List<ArticleModel> = emptyList(),
    val userSubjects: List<String> = emptyList(),
    val filteredSubjects: List<String> = emptyList(),

    val bottomSheetSubjectsMap: Map<String, Boolean> = emptyMap(),
)
