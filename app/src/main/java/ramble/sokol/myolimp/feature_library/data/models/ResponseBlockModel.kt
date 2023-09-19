package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class ResponseBlockModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("text") val blockText: String? = null,
    @SerializedName("sequence_number") val sequenceNum: Int = -1,
    @SerializedName("block_type") val type: String = "",
    @SerializedName("is_difficult") val isDifficult: Boolean? = null,
    @SerializedName("questions") val listQuestions: List<ResponseQuestionModel> = listOf()
)