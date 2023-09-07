package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class UserEducationDataModel(
    @SerializedName("region") val region: Int = -1,
    @SerializedName("city") val city: String = "",
    @SerializedName("school") val schoolId: Int = -1,
    @SerializedName("grade") val grade: Int? = null
)
