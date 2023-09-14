package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.util.Log
import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository {

    private val instance = RetrofitBuilder().instance(RegistrationApi::class.java)

    fun registerInfo(
        auth: String,
        data: UserMainDataModel,
        onResult: (ResponseUserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateMainUserData(auth, data).enqueue(
            object : Callback<ResponseUserModel> {
                override fun onResponse(
                    call: Call<ResponseUserModel>,
                    response: Response<ResponseUserModel>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun registerEducation(
        auth: String,
        data: UserEducationDataModel,
        onResult: (ResponseUserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateEducationUserData(auth, data).enqueue(
            object : Callback<ResponseUserModel> {
                override fun onResponse(
                    call: Call<ResponseUserModel>,
                    response: Response<ResponseUserModel>
                ) {
                    Log.i("RegistrationEducationViewModel","auth token: $auth")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }


    fun registerImageDocs(
        auth: String,
        data: UserDocsDataModel,
        imageBody: MultipartBody.Part,
        onResult: (ResponseUserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.uploadImage(auth, imageBody).enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.i("RegistrationRepository", "Image status ${response.code()}, body: ${response.body()}")  // TODO()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onError(t)
                }

            }
        )
        instance.updateDocsUserData(auth, data).enqueue(
            object : Callback<ResponseUserModel> {
                override fun onResponse(
                    call: Call<ResponseUserModel>,
                    response: Response<ResponseUserModel>
                ) {
                    Log.i("RegistrationRepository", "Docs status ${response.code()}, mes: ${response.message()}")  // TODO()
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

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
        data: Int,
        onResult: (List<ResponseCityModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getCities(auth, data).enqueue(
            object : Callback<List<ResponseCityModel>> {
                override fun onResponse(
                    call: Call<List<ResponseCityModel>>,
                    response: Response<List<ResponseCityModel>>
                ) {
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
        data: Int,
        onResult: (List<ResponseSchoolModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getSchools(auth, data).enqueue(
            object : Callback<List<ResponseSchoolModel>> {
                override fun onResponse(
                    call: Call<List<ResponseSchoolModel>>,
                    response: Response<List<ResponseSchoolModel>>
                ) {
                    Log.i("RegistrationEducationViewModel","request success with ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<ResponseSchoolModel>>, t: Throwable) {
                    Log.i("RegistrationEducationViewModel","request called ${t.message} with exception")
                    onError(t)
                }
            }
        )
    }

}
