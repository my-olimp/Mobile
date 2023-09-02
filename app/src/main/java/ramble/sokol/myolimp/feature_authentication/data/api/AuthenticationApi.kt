package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSendingEmailModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSignUpModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationApi {
    @Headers("Content-Type: application/json")
    @POST("user/auth/login/")
    fun loginUser(
        @Body data: RequestLoginModel,
    ): Call<ResponseAuthModel>

    @Headers("Content-Type: application/json")
    @POST("user/register/")
    fun signUp(
        @Body data: RequestSignUpModel,
    ): Call<ResponseAuthModel>

    @Headers("Content-Type: application/json")
    @POST("user/register/email/")
    fun sendEmail(
        @Body data: RequestSendingEmailModel,
    ): Call<String>

}
