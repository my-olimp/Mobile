package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName

data class ResponseAnswerModel (
    @SerializedName("is_correct") val isCorrect: Boolean = false
)