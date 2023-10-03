package ramble.sokol.myolimp.feature_profile.data.models

import com.google.gson.annotations.SerializedName

data class ResponseSnilsError(
    @SerializedName("SNILS") val snilsError: String = ""
)