package ramble.sokol.myolimp.feature_library.data.api

import ramble.sokol.myolimp.feature_library.data.models.RequestAnswerModel
import ramble.sokol.myolimp.feature_library.data.models.ResponseAnswerModel
import ramble.sokol.myolimp.feature_library.data.models.ResponseArticleModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface LibraryApi {

    @Headers("Content-Type: application/json",)
    @GET("library/article/{id}")
    fun extractArticleById(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Call<ResponseArticleModel>

    @Headers("Content-Type: application/json")
    @POST("library/block/question/check/{id}/")
    fun checkAnswer(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Body body : RequestAnswerModel
    ) : Call<ResponseAnswerModel>

}