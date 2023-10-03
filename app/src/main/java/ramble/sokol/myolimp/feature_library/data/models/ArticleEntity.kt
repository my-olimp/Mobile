package ramble.sokol.myolimp.feature_library.data.models

import ramble.sokol.myolimp.feature_library.data.results.Author
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class ArticleEntity(
    val author: Author,
    val id: Int,
    val image: Any?,
    val subject: String,
    val tags: List<String>,
    val title: String
) {
    fun toArticleModel() = ArticleModel(
        id = id,
        subject = subject,
        title = title
    )
}
