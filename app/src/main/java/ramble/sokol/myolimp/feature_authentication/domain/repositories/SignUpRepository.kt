package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.util.Log
import ramble.sokol.myolimp.feature_authentication.data.api.AuthenticationApi
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSendingEmailModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSignUpModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {

    private val instance = ProfileRetrofitInstance().instance(AuthenticationApi::class.java)

    fun signUp(
        data: RequestSignUpModel,
        onResult: (ResponseAuthModel?) -> Unit,
        onError: (Throwable) -> Unit
    ){
        instance.signUp(data).enqueue(
            object : Callback<ResponseAuthModel> {

                override fun onResponse(
                    call: Call<ResponseAuthModel>,
                    response: Response<ResponseAuthModel>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun sendVerificationCode(
        data: RequestSendingEmailModel,
        onResult: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.sendEmail(data).enqueue(
            object : Callback<String> {

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(
                    call: Call<String>,
                    t: Throwable
                ) {
                    onError(t)
                }
            }
        )
    }
}
