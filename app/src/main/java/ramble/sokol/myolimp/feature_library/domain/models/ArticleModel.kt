package ramble.sokol.myolimp.feature_library.domain.models

import ramble.sokol.myolimp.feature_profile.domain.models.SavedArticleModel

data class ArticleModel (
    val id: Int = -1,
    val title: String = "",
    val author : AuthorModel = AuthorModel(),
    val tags: List<String> = listOf(),
    val subject: String = "",
    val image: String = "",
    val isFavourite: Boolean = false,
    val blocks: List<BlockModel> = listOf()
) {
    fun toSavedArticle() = SavedArticleModel(
        id = id,
        title = title,
        authorModel = "${author.firstName} ${author.secondName} ${author.thirdName}",
        tags = tags,
        subject = subject,
        isFavourite = isFavourite,
    )
}
