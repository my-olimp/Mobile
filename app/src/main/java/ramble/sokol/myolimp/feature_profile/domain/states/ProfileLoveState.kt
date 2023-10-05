package ramble.sokol.myolimp.feature_profile.domain.states

import ramble.sokol.myolimp.feature_profile.data.models.ArticleModel

data class ProfileLoveState(
    val isLoaded: Boolean = false,

    val listArticles: List<ArticleModel> = emptyList(),
    val subjects: List<String> = emptyList(),
)