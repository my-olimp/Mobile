package ramble.sokol.myolimp.feature_profile.data.api

import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @Headers(
        "Content-Type: application/json",
    )
    @PATCH("user/")
    suspend fun updateUserData(
//        @Header("Authorization") auth: String,
        @Body user: LocalUserModel,
    ): Response<LocalUserModel>


    @Multipart
    @POST("/user/avatar/")
    suspend fun uploadImage(
        @Header("Authorization") auth: String,
        @Part image: MultipartBody.Part
    ): Call<Void>?

    @Headers("Content-Type: application/json")
    @POST("user/auth/logout/")
    suspend fun logOut(
        @Header("Cookie") cookie: String,
    ) : Response<Unit>

    @Headers("Content-Type: application/json")
    @POST("user/auth/refresh_token/")
    suspend fun refreshToken(
        @Header("Cookie") cookie: String,
    ) : Response<ResponseAuthModel>

    @Headers("Content-Type: application/json")
    @GET("user/location/cities")
    fun getCities(
        @Header("Authorization") auth: String,
        @Query("region") data: Int
    ) : Call<List<ResponseCityModel>>


    @Headers("Content-Type: application/json")
    @GET("user/location/schools")
    fun getSchools(
        @Header("Authorization") auth: String,
        @Query("region") data: Int
    ) : Call<List<ResponseSchoolModel>>

    @Headers("Content-Type: application/json")
    @GET("user/location/regions")
    fun getRegions(
        @Header("Authorization") auth: String
    ) : Call<List<ResponseRegionModel>>

}
