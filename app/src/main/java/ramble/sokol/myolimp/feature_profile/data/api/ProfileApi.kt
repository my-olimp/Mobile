package ramble.sokol.myolimp.feature_profile.data.api

import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProfileApi {

    @Headers(
        "Content-Type: application/json",
    )
    @PATCH("user/")
    suspend fun updateUserData(
        @Header("Authorization") auth: String,
        @Body user: UserModelEntity,
    ): Response<UserModelEntity>


    @Headers("Content-Type: application/json")
    @POST("user/avatar/")
    suspend fun updateUserImg(
        @Header("Authorization") auth: String,
        @Body imgArray: String,
    )

    @Headers("Content-Type: application/json")
    @POST("user/auth/logout/")
    suspend fun logOut(
        @Header("Cookie") cookie: String,
    ) : Response<Unit>

    @Headers("Content-Type: application/json")
    @POST("user/auth/refresh_token/")
    suspend fun refreshToken(
        @Header("Cookie") cookie: String,
    ) : Call<ResponseAuthModel>

}
