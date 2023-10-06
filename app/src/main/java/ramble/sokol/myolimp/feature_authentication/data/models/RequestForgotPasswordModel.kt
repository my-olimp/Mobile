package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class RequestForgotPasswordModel(
    @SerializedName("email") val email: String = ""
)
