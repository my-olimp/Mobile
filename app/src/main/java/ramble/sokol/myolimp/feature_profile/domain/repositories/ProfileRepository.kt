package ramble.sokol.myolimp.feature_profile.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import ramble.sokol.myolimp.feature_profile.domain.models.UserModel

class ProfileRepository(
    context: Context
) {

    private val instance = RetrofitBuilder(context = context).instance(ProfileApi::class.java)

    suspend fun updateUser(
        auth: String,
        user: UserModel
    ) = ProfileRetrofitInstance.api.updateUserData(
            auth = auth,
            user = user.toUserModelEntity()
        )

    suspend fun updateUserImg(
        auth: String,
        imgArray: String
    ) = ProfileRetrofitInstance.api.updateUserImg(
        auth = auth,
        imgArray = imgArray
    )

    suspend fun logOut(
        cookie: String,
        onResult: () -> Unit,
        onError: (Throwable) -> Unit
    ) {

        try {
            instance.logOut(
                cookie = cookie
            )
            onResult()
        } catch (ex: Exception) {
            onError(ex)
        }
    }

}
