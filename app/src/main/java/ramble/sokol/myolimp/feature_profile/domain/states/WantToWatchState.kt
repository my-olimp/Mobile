package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.domain.models.SavedArticleModel
import ramble.sokol.myolimp.utils.State

data class WantToWatchState (
    val queryText: String = "",

    val savedArticles: List<SavedArticleModel> = emptyList(),
    val subjects: HashMap<String,Boolean> = HashMap(),

    val userSubjects: List<String> = emptyList(),
    val filteredSubjects: List<String> = emptyList(),

    val isLoading : Boolean = false,
    val isError: Boolean = false,
    val isNetworkError:Boolean = false,
) : State<WantToWatchState> {
    override val tag: String
        get() = "ViewModelWantWatch"

    override fun onError(): WantToWatchState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): WantToWatchState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): WantToWatchState {
        return this.copy(isLoading = value)
    }
}
