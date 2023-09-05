package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository (
    val context: Context
) {
    private val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

    fun registerInfo(
        auth: String,
        data: UserMainDataModel,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateMainUserData(auth,data).enqueue(
            object : Callback<UserModelEntity> {
                override fun onResponse(call: Call<UserModelEntity>, response: Response<UserModelEntity>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModelEntity>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun registerEducation(
        auth: String,
        data: UserEducationDataModel,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateEducationUserData(auth, data).enqueue(
            object : Callback<UserModelEntity> {
                override fun onResponse(call: Call<UserModelEntity>, response: Response<UserModelEntity>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModelEntity>, t: Throwable) {
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
        onResult: (List<String>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getCities(auth, data).enqueue(
            object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}