package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.data.models.ArticleModel
import ramble.sokol.myolimp.utils.State

data class ProfileLoveState(
    val isArticlesLoaded: Boolean = false,
    val isSubjectLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError:Boolean = false,

    val queryText: String = "",

    val listArticles: List<ArticleModel> = emptyList(),
    val subjects: HashMap<String,Boolean> = HashMap(),

    val userSubjects: List<String> = emptyList(),
    val filteredSubjects: List<String> = emptyList()
) : State<ProfileLoveState> {
    override val tag: String
        get() = "ProfileLoveState"

    override fun onError(): ProfileLoveState {
        return this.copy(isError = true)
    }

    override fun onNetworkError(): ProfileLoveState {
        return this.copy(isNetworkError = true)
    }

    override fun onLoaderUpdate(value: Boolean): ProfileLoveState {
        return this.copy(isLoading = value)
    }
}