package ramble.sokol.myolimp.feature_profile.domain.repositories

import android.util.Log
import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileRepository {

    private val instance = RetrofitBuilder().instance(ProfileApi::class.java)

    suspend fun updateUser(
        auth: String,
        user: LocalUserModel
    ) = instance.updateUserData(
            auth = auth,
            user = user
        )

    suspend fun uploadImg(
        auth: String,
        imageBody: MultipartBody.Part,
        onResult: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            instance.uploadImage(
                auth = auth,
                image = imageBody
            )
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

    fun refreshToken(
        cookie: String,
        onResult: (ResponseAuthModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
//        instance.refreshToken(cookie = cookie).enqueue(
//            object : Callback<ResponseAuthModel> {
//
//                override fun onResponse(
//                    call: Call<ResponseAuthModel>,
//                    response: Response<ResponseAuthModel>
//                ) {
//                    onResult(response.body())
//                }
//
//                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {
//                    onError(t)
//                }
//            }
//        )
    }

    suspend fun refreshTokenNew(cookie: String)
        = instance.refreshToken(cookie)

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
