package ramble.sokol.myolimp.feature_authentication.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.AuthenticationApi
import ramble.sokol.myolimp.feature_authentication.data.models.RequestLoginModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    private val instance = ProfileRetrofitInstance().instance(AuthenticationApi::class.java)

    fun login (
        data: RequestLoginModel,
        onResult: (ResponseAuthModel?) -> Unit,
        onError: (Throwable) -> Unit
    ){

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
