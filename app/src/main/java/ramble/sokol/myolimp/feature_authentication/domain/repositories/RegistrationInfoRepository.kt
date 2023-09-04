package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationInfoRepository (
    val context: Context
) {

    fun registerInfo(
        auth: String,
        data: UserMainDataModel,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

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
}