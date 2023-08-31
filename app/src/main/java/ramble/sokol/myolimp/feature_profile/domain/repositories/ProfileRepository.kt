package ramble.sokol.myolimp.feature_profile.domain.repositories

import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.data.models.UserModel

class ProfileRepository {

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

}
