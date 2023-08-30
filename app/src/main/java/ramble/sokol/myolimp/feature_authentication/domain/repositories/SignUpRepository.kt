package ramble.sokol.myolimp.feature_authentication.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.AuthenticationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseLoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {
    fun signUp(
        data: RequestLoginModel,
        onResult: (ResponseLoginModel?) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val instance = RetrofitBuilder.instance(AuthenticationApi::class.java)

        instance.loginUser(data).enqueue(
            object : Callback<ResponseLoginModel> {

                override fun onResponse(
                    call: Call<ResponseLoginModel>,
                    response: Response<ResponseLoginModel>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseLoginModel>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}