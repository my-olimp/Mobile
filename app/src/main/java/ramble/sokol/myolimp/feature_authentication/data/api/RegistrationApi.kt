package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel

import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Query
import retrofit2.http.POST
import retrofit2.http.Part

interface RegistrationApi {

    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun updateMainUserData(
        @Header("Authorization") auth: String,
        @Body data: UserMainDataModel
    ) : Call<UserModelEntity>

    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun updateEducationUserData(
        @Header("Authorization") auth: String,
        @Body data: UserEducationDataModel
    ) : Call<UserModelEntity>

    @Headers("Content-Type: application/json")
    @GET("user/location/regions")
    fun getRegions(
        @Header("Authorization") auth: String
    ) : Call<List<ResponseRegionModel>>

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

    @PATCH("user/")
    fun updateDocsUserData(
        @Header("Authorization") auth: String,
        @Body data: UserDocsDataModel
    ) : Call<UserModelEntity>

    @Multipart
    @POST("/user/avatar/")
    fun uploadImage(
        @Header("Authorization") auth: String,
        @Part image: MultipartBody.Part
    ): Call<Void>
}
