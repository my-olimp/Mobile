package ramble.sokol.myolimp.feature_library.data.remote

import ramble.sokol.myolimp.feature_library.data.results.GetArticlesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryAPI {
    @GET("library/article/search/{page}/")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("love") love: Int,
        @Query("query") query: String,
    ): Response<GetArticlesResult>
}
