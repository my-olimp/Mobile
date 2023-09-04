package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationRepository(
    val context: Context
) {

    fun registerInfo(
        auth: String,
        data: UserMainDataModel,
        onResult: (UserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

        instance.updateMainUserData(auth,data).enqueue(
            object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun registerEducation(
        auth: String,
        data: UserEducationDataModel,
        onResult: (UserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

        instance.updateEducationUserData(auth, data).enqueue(
            object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}