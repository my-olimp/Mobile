package ramble.sokol.myolimp.feature_authentication.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.AuthenticationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    fun login (
        data: RequestLoginModel,
        onResult: (ResponseAuthModel?) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val instance = RetrofitBuilder().instance(AuthenticationApi::class.java)

        instance.loginUser(data).enqueue(
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
}
