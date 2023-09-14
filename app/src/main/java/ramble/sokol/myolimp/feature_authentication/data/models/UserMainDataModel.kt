package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class UserMainDataModel (
    @SerializedName("first_name") val firstName: String = "",
    @SerializedName("second_name") val secondName: String = "",
    @SerializedName("third_name") val thirdName: String = "",
    @SerializedName("data_of_birth") val dateOfBirth: String = "",
    @SerializedName("gender") val gender: String = "",
    @SerializedName("account_type") val accountType: String = ""
)
