package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class UserEducationDataModel(
    @SerializedName("region") val region: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("school") val school: String = "",
    @SerializedName("grade") val grade: Int? = null
)
