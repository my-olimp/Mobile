package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class ResponseAuthorModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("first_name") val fisrtName: String? = null,
    @SerializedName("second_name") val secondName : String? = null,
    @SerializedName("third_name") val thirdName: String? = null
)