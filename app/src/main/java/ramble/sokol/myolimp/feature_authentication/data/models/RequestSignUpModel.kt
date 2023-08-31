package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RequestSignUpModel(
    @SerializedName("code") val code: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("second_name") val secondName: String,
    @SerializedName("third_name") val thirdName: String,
    @SerializedName("password") val password: String,
) : Serializable
