package ramble.sokol.myolimp.feature_authentication.data.models

import com.google.gson.annotations.SerializedName

data class UserDocsDataModel(
    @SerializedName("SNILS") val snils: String = ""
)
