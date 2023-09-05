package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel

import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.Query

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
    ) : Call<List<String>>


    @Headers("Content-Type: application/json")
    @GET("user/location/cities")
    fun getSchools(
        @Header("Authorization") auth: String,
        @Query("region") data: Int
    ) : Call<List<String>> /*TODO replace*/

}
