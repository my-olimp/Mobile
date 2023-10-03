package ramble.sokol.myolimp.feature_library.domain.models

data class QuestionModel(
    val id: Int = -1,
    val questionText: String = "",
    val answer: String = "",
    val explanation: String = "",
    val blockId: Int = -1
)