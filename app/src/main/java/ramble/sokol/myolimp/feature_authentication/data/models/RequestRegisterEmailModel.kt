package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class RequestRegisterEmailModel(
    @SerializedName("email") val email: String = ""
)