package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.RequestForgotPasswordModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestRegisterEmailModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SendCodeApi {

    @Headers("Content-Type: application/json")
    @POST("/user/forgot/")
    fun sendForgotEmail(
        @Body email: RequestForgotPasswordModel
    ) : Call<Unit>

    @Headers("Content-Type: application/json")
    @POST("/user/register/email/")
    fun sendRegisterEmail(
        @Body email: RequestRegisterEmailModel
    ) : Call<Unit>

}