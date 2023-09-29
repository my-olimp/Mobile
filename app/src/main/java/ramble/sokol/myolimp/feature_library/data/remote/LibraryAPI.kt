package ramble.sokol.myolimp.feature_library.data.remote

import ramble.sokol.myolimp.feature_library.data.results.GetArticlesResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface LibraryAPI {
    @GET("library/article/search/1/")
    suspend fun getArticles(
        @Header("Authorization") auth: String,
    ): GetArticlesResult
}