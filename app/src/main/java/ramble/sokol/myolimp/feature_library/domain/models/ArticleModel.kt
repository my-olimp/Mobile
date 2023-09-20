package ramble.sokol.myolimp.feature_library.domain.models

data class ArticleModel(
    val id: Int = 0,
    val type: String = "Статья",
    val subject: String = "",
    val title: String = ""
)
