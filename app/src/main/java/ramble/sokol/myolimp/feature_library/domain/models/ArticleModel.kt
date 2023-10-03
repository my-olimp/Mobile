package ramble.sokol.myolimp.feature_library.domain.models

data class ArticleModel (
    val id: Int = -1,
    val title: String = "",
    val author : AuthorModel = AuthorModel(),
    val tags: List<String> = listOf(),
    val subject: String = "",
    val image: String = "",
    val isFavourite: Boolean = false,
    val blocks: List<BlockModel> = listOf()
)
