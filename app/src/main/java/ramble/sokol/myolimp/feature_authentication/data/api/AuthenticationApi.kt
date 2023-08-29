package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseLoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationApi {
    @Headers("Content-Type: application/json")
    @POST("user/login/")
    fun loginUser(
        @Body data: RequestLoginModel,
    ): Call<ResponseLoginModel>

}
