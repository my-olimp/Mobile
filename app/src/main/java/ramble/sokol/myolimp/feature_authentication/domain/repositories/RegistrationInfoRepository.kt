package ramble.sokol.myolimp.feature_authentication.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import retrofit2.Response


class RegistrationInfoRepository {

    fun registerInfo(
        auth: String,
        data: UserMainDataModel
    ): Response<UserModel> {
        val instance = RetrofitBuilder.instance(RegistrationApi::class.java)

        return instance.updateMainUserData(
            auth = auth,
            data = data
        )
    }
}