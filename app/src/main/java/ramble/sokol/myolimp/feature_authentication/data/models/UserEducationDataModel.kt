package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class UserEducationDataModel(
    @SerializedName("region") val regionId: Int = -1,
    @SerializedName("city") val cityId: Int = -1,
    @SerializedName("school") val schoolId: Int = -1,
    @SerializedName("grade") val grade: Int? = null
)
