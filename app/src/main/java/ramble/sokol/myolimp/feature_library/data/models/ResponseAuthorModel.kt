package ramble.sokol.myolimp.feature_library.data.models

import com.google.gson.annotations.SerializedName
import ramble.sokol.myolimp.feature_library.domain.models.AuthorModel

data class ResponseAuthorModel(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("second_name") val secondName : String? = null,
    @SerializedName("third_name") val thirdName: String? = null
) {
    fun asAuthorModel() = AuthorModel(
        id = id,
        firstName = firstName ?: "",
        secondName = secondName ?: "",
        thirdName = thirdName ?: ""
    )
}