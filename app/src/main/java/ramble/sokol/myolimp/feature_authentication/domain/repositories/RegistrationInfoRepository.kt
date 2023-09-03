package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import retrofit2.Response


class RegistrationInfoRepository (
    val context: Context
) {

    fun registerInfo(
        auth: String,
        data: UserMainDataModel
    ): Response<UserModelEntity> {
        val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

        return instance.updateMainUserData(
            auth = auth,
            data = data
        )
    }
}