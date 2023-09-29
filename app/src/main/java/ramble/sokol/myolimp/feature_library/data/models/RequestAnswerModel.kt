package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class RequestAnswerModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("answer") val answer: String = ""
)