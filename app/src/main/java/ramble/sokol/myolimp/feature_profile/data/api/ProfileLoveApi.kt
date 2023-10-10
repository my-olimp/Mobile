package ramble.sokol.myolimp.feature_profile.data.api

import ramble.sokol.myolimp.feature_profile.data.models.ResponseSearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileLoveApi {
    @GET("library/article/search/{page}/")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("love") love: Int,
        @Query("query") query: String,
    ): Response<ResponseSearchModel>

    @GET("user/subjects/")
    suspend fun getSubjects(
        @Header("Authorization") auth: String
    ): Response<List<String>>
}