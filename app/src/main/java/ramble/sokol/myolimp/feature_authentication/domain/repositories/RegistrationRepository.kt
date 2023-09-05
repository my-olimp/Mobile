package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import android.util.Log
import okhttp3.MultipartBody
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationRepository(
    val context: Context
) {

    fun registerInfo(
        auth: String,
        data: UserMainDataModel,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

        instance.updateMainUserData(auth, data).enqueue(
            object : Callback<UserModelEntity> {
                override fun onResponse(
                    call: Call<UserModelEntity>,
                    response: Response<UserModelEntity>
                ) {
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
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

        instance.updateEducationUserData(auth, data).enqueue(
            object : Callback<UserModelEntity> {
                override fun onResponse(
                    call: Call<UserModelEntity>,
                    response: Response<UserModelEntity>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModelEntity>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }


    fun registerImageDocs(
        auth: String,
        data: UserDocsDataModel,
        imageBody: MultipartBody.Part,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)
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
            object : Callback<UserModelEntity> {
                override fun onResponse(
                    call: Call<UserModelEntity>,
                    response: Response<UserModelEntity>
                ) {
                    Log.i("RegistrationRepository", "Docs status ${response.code()}, mes: ${response.message()}")  // TODO()
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModelEntity>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}