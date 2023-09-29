package ramble.sokol.myolimp.feature_library.presenation.mainScreen

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class LibraryState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val isShowingFavourites: Boolean = false,
    val articles: List<ArticleModel> = emptyList(),
    val userSubjects: List<String> = emptyList(),

    val bottomSheetSubjectsMap: Map<String, Boolean> = emptyMap(),
    val filteredSubjects: List<String> = emptyList(),
)
