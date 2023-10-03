package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.RequestForgotPasswordModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SendCodeApi {

    @Headers("Content-Type: application/json")
    @POST("/user/forgot/")
    fun sendEmail(
        @Body email: RequestForgotPasswordModel
    ) : Call<Unit>
}