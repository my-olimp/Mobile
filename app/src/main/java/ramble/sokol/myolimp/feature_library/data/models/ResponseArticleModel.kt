package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class ResponseArticleModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("author") val authorModel: ResponseAuthorModel = ResponseAuthorModel(),
    @SerializedName("tags") val listTags: List<String>,
    @SerializedName("subject") val subject: String = "",
    @SerializedName("love") val isFavourite: Boolean = false,
    @SerializedName("image") val image: String? = null,
    @SerializedName("blocks") val listBlocks : List<ResponseBlockModel> = listOf()
) {
    fun asArticleModel() = ArticleModel(
        id = id,
        title = title,
        author = authorModel.asAuthorModel(),
        tags = listTags,
        subject = subject,
        isFavourite = isFavourite,
        image = image ?: "",
        blocks = if(listBlocks.isNotEmpty())listBlocks.toListBlocks() else listOf()
    )
}