package ramble.sokol.myolimp.feature_library.data.api

import ramble.sokol.myolimp.feature_library.data.results.GetArticlesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChapterApi {
    @GET("library/article/search/{page}/")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("subject") subject: String,
        @Query("query") query: String,
        @Query("love") love: Int,
    ): Response<GetArticlesResult>
}
