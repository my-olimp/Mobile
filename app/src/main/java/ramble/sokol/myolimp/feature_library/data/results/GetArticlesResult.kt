package ramble.sokol.myolimp.feature_library.data.results

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.data.models.ArticleEntity

data class GetArticlesResult(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("page_count")
    val pageCount: Int,
    @SerializedName("pages")
    val articles: List<ArticleEntity>
)