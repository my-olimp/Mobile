package ramble.sokol.myolimp.feature_profile.data.models

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserModel (
    @SerializedName("first_name") val firstName: String,
    @SerializedName("second_name") val secondName: String,
    @SerializedName("third_name") val thirdName: String,
    @SerializedName("data_of_birth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("snils") val snils: String,
    @SerializedName("region") val region: String,
    @SerializedName("city") val city: String,
    @SerializedName("school") val school: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("grade") val grade: Int,
    @SerializedName("image") val profileImg: Uri?,
    val hasThird: Boolean,
) : Serializable
