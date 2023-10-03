package ramble.sokol.myolimp.feature_library.domain.models

data class BlockModel(
    val id: Int = -1,
    val blockText: String = "",
    val sequenceNumber: Int = -1,
    val type: String = "",
    val isDifficult: Boolean = false,
    val questions: List<QuestionModel> = listOf()
)