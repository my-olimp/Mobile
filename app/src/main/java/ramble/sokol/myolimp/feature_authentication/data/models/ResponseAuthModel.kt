package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import java.io.Serializable

data class ResponseAuthModel (
    @SerializedName("access") val code: String,
    @SerializedName("user") val user: ResponseUserModel
) : Serializable
