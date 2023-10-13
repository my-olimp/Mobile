package ramble.sokol.myolimp.feature_profile.domain.repositories

import android.util.Log
import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.data.models.RequestUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileRepository {

    private val instance = RetrofitBuilder().instance(ProfileApi::class.java)
    private val profileInstance = ProfileRetrofitInstance().instance(ProfileApi::class.java)

    suspend fun updateUser(
        user: RequestUserModel
    ) = instance.updateUserData(
            user = user
        )

    suspend fun uploadImg(
        imageBody: MultipartBody.Part,
        onResult: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            instance.uploadImage(
                image = imageBody
            )
            onResult()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    suspend fun deleteImg(
        onResult: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            instance.deleteImg()
            onResult()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    suspend fun logOut(
        cookie: String,
        onResult: () -> Unit,
        onError: (Throwable) -> Unit
    ) {

        try {
            instance.logOut(
                cookie = cookie
            )
            onResult()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

    suspend fun refreshToken(cookie: String)
        = profileInstance.refreshToken(cookie)

    fun getRegions(
        auth: String,
        onResult: (List<ResponseRegionModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getRegions(auth).enqueue(
            object : Callback<List<ResponseRegionModel>> {
                override fun onResponse(
                    call: Call<List<ResponseRegionModel>>,
                    response: Response<List<ResponseRegionModel>>
                ) {
                    Log.i("ViewModelProfile","region response code: ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<ResponseRegionModel>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
    fun getCities(
        auth: String,
        regionId: Int,
        onResult: (List<ResponseCityModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getCities(auth,regionId).enqueue(
            object: Callback<List<ResponseCityModel>> {
                override fun onResponse(
                    call: Call<List<ResponseCityModel>>,
                    response: Response<List<ResponseCityModel>>
                ) {
                    Log.i("ViewModelProfile","city response code: ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<ResponseCityModel>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun getSchools(
        auth: String,
        regionId: Int,
        onResult: (List<ResponseSchoolModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getSchools(auth,regionId).enqueue(
            object: Callback<List<ResponseSchoolModel>> {
                override fun onResponse(
                    call: Call<List<ResponseSchoolModel>>,
                    response: Response<List<ResponseSchoolModel>>
                ) {
                    Log.i("ViewModelProfile","school response code ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<ResponseSchoolModel>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}
