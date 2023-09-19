package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class ResponseArticleModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("author") val authorModel: ResponseAuthorModel = ResponseAuthorModel(),
    @SerializedName("tags") val listTags: List<String> /*TODO tags???*/,
    @SerializedName("subject") val subject: String = "",
    @SerializedName("image") val image: String? = null,
    @SerializedName("blocks") val listBlocks : List<ResponseBlockModel> = listOf()
)