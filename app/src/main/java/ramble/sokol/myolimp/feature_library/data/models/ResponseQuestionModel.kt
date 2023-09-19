package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class ResponseQuestionModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("text") val questionText: String = "",
    @SerializedName("answer") val answer: String = "",
    @SerializedName("explanation") val explanation: String = "",
    @SerializedName("block") val block: Int = -1 /*TODO wtf*/
)