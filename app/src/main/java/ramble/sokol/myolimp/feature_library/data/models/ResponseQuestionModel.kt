package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.domain.models.QuestionModel

data class ResponseQuestionModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("text") val questionText: String = "",
    @SerializedName("answer") val answer: String = "",
    @SerializedName("explanation") val explanation: String = "",
    @SerializedName("block") val block: Int = -1 /*TODO wtf*/
) {
    fun asQuestionModel() = QuestionModel(
        id = id,
        questionText = questionText,
        answer = answer,
        explanation = explanation,
        blockId = block
    )

}


fun List<ResponseQuestionModel>.toListQuestions(): List<QuestionModel> {
    val list = mutableListOf<QuestionModel>()
    for(item in this)list.add(item.asQuestionModel())
    return list
}
