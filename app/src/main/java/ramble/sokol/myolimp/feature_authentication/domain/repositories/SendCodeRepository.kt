package ramble.sokol.myolimp.feature_authentication.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.SendCodeApi
import ramble.sokol.myolimp.feature_authentication.data.models.RequestForgotPasswordModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestRegisterEmailModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendCodeRepository {

    private val instance = ProfileRetrofitInstance().instance(SendCodeApi::class.java)

    fun sendForgotEmail(
        body: RequestForgotPasswordModel,
        onResult: (Int) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.sendForgotEmail(body).enqueue(
            object: Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    onResult(response.code())
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun sendRegisterEmail(
        body: RequestRegisterEmailModel,
        onResult: (Int) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.sendRegisterEmail(body).enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    onResult(response.code())
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}