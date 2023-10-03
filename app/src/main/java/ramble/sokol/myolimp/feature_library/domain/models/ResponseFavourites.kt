package ramble.sokol.myolimp.feature_library.domain.models

import com.google.gson.annotations.SerializedName

data class ResponseFavourites (
    @SerializedName("love") val isFavourite: Boolean = false
)
