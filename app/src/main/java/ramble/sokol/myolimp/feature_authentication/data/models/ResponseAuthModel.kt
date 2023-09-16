package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserModel
import java.io.Serializable

data class ResponseAuthModel (
    @SerializedName("access") val code: String,
    @SerializedName("user") val user: LocalUserModel
) : Serializable
