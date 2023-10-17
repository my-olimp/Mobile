package ramble.sokol.myolimp.feature_library.presenation.mainScreen

import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.utils.State

data class LibraryState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isShowingFavourites: Boolean = false,

    val searchQuery: String = "",

    val currentPage: Int = 1,
    val amountOfPages: Int = 1,

    val articles: List<ArticleModel> = emptyList(),
    val userSubjects: List<String> = emptyList(),
    val filteredSubjects: List<String> = emptyList(),

    val bottomSheetSubjectsMap: Map<String, Boolean> = emptyMap(),
) : State<LibraryState> {
    override val tag: String
        get() = "LibraryState"

    override fun onError(): LibraryState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): LibraryState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): LibraryState {
        return this.copy(isLoading = value)
    }

}
