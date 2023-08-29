package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestLoginModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
): Serializable
