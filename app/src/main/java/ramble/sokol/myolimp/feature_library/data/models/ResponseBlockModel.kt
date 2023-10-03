package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.domain.models.BlockModel
import ramble.sokol.myolimp.feature_library.domain.models.QuestionModel

data class ResponseBlockModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("text") val blockText: String = "",
    @SerializedName("sequence_number") val sequenceNum: Int = -1,
    @SerializedName("block_type") val type: String = "",
    @SerializedName("is_difficult") val isDifficult: Boolean = false,
    @SerializedName("questions") val listQuestions: List<ResponseQuestionModel> = listOf()
) {
    fun asBlockModel() = BlockModel(
        id = id,
        blockText = blockText,
        sequenceNumber = sequenceNum,
        type = type,
        isDifficult = isDifficult,
        questions = if(listQuestions.isNotEmpty())listQuestions.toListQuestions() else listOf()
    )
}


fun List<ResponseBlockModel>.toListBlocks(): List<BlockModel> {
    val list = mutableListOf<BlockModel>()
    for(item in this)list.add(item.asBlockModel())
    return list
}
