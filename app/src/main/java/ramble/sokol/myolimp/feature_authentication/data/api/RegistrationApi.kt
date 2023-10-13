package ramble.sokol.myolimp.feature_authentication.data.api

import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RegistrationApi {

    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun updateMainUserData(
        @Body data: UserMainDataModel
    ) : Call<ResponseUserModel>

    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun updateEducationUserData(
        @Body data: UserEducationDataModel
    ) : Call<ResponseUserModel>

    @Headers("Content-Type: application/json")
    @GET("user/location/regions")
    fun getRegions() : Call<List<ResponseRegionModel>>

    @Headers("Content-Type: application/json")
    @GET("user/location/cities")
    fun getCities(
        @Query("region") data: Int
    ) : Call<List<ResponseCityModel>>


    @Headers("Content-Type: application/json")
    @GET("user/location/schools")
    fun getSchools(
        @Query("region") data: Int
    ) : Call<List<ResponseSchoolModel>>

    @Headers("Content-Type: application/json")
    @GET("user/subjects/")
    fun getSubjects() : Call<List<String>>

    @PATCH("user/")
    fun updateSnils(
        @Body data: UserDocsDataModel
    ) : Call<ResponseUserModel>

    @Multipart
    @POST("/user/avatar/")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @PATCH("user/")
    fun updateSubjects(
        @Body data: RequestSubjects
    ) : Call<ResponseUserModel>
}
