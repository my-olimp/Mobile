package ramble.sokol.myolimp.feature_profile.data.models

import ramble.sokol.myolimp.feature_library.domain.models.AuthorModel

data class ArticleModel(
    val id: Int = -1,
    val title: String = "",
    val authorModel: AuthorModel = AuthorModel(),
    val listTags: List<String> = emptyList(),
    val subject: String = "",
    val isFavourite: Boolean = false,
    val createdAt: String = "",
    val views: Int = -1
)
