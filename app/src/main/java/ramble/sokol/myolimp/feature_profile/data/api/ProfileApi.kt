package ramble.sokol.myolimp.feature_profile.data.api

import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PUT

interface ProfileApi {

    @Headers(
        "Content-Type: application/json",
    )
    @PUT("user/")
    suspend fun updateUserData(
        @Header("Authorization") auth: String,
        @Body user: UserModel,
    ): Response<UserModel>

}
