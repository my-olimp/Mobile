package ramble.sokol.myolimp.feature_library.data.results

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("first_name")
    val firstName: String,
    val id: Int,
    @SerializedName("second_name")
    val secondName: String,
    @SerializedName("third_name")
    val thirdName: String?
)