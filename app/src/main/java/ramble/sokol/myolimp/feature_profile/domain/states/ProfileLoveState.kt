package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.data.models.ArticleModel

data class ProfileLoveState(
    val isArticlesLoaded: Boolean = false,
    val isSubjectLoaded: Boolean = false,

    val queryText: String = "",

    val listArticles: List<ArticleModel> = emptyList(),
    val subjects: HashMap<String,Boolean> = HashMap(),

    val userSubjects: List<String> = emptyList(),
    val filteredSubjects: List<String> = emptyList()
)