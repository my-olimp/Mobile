package ramble.sokol.myolimp.feature_profile.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.AuthenticationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseAuthModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {

    private val instance = RetrofitBuilder.instance(ProfileApi::class.java)

    suspend fun updateUser(
        auth: String,
        user: UserModel
    ) = ProfileRetrofitInstance.api.updateUserData(
            auth = auth,
            user = user
        )

    suspend fun updateUserImg(
        auth: String,
        imgArray: String
    ) = ProfileRetrofitInstance.api.updateUserImg(
        auth = auth,
        imgArray = imgArray
    )

    suspend fun logOut(
        auth: String,
        onResult: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.logOut(auth).enqueue(
            object : Callback<String> {

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    onResult(response.body().toString())
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
