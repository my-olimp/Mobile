package ramble.sokol.myolimp.feature_profile.data.models

import com.google.gson.annotations.SerializedName

data class ResponseSearchModel(
    @SerializedName("page_count") val pageCount: Int = 0,
    @SerializedName("current_page") val currentPage: Int = 0,
    @SerializedName("pages") val articles: List<ResponseArticlesModel> = emptyList()
) {
    fun toArticlesList() : List<ArticleModel> {
        val list = mutableListOf<ArticleModel>()
        for(item in articles) list.add(item.toArticleModel())
        return list
    }
}