package ramble.sokol.myolimp.feature_profile.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.data.models.ResponseAuthorModel

data class ResponseArticlesModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("author") val authorModel: ResponseAuthorModel = ResponseAuthorModel(),
    @SerializedName("tags") val listTags: List<String> = emptyList(),
    @SerializedName("subject") val subject: String = "",
    @SerializedName("love") val isFavourite: Boolean = false,
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("views") val views: Int = -1
) {
    fun toArticleModel() = ArticleModel(
        id = id,
        title = title,
        authorModel = authorModel.asAuthorModel(),
        listTags = listTags,
        subject = subject,
        isFavourite = isFavourite,
        createdAt = createdAt,
        views = views
    )
}