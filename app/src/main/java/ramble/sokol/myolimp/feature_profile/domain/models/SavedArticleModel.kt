package ramble.sokol.myolimp.feature_profile.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    "articles"
)
data class SavedArticleModel (
    @SerializedName("id") @PrimaryKey val id: Int = -1,
    @SerializedName("views") val views: Int = -1,
    @SerializedName("title") val title: String = "",
    @SerializedName("author") val authorModel: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("subject") val subject: String = "",
    @SerializedName("is_favourite") val isFavourite: Boolean = false,
    @SerializedName("tags") @Embedded val tags: List<String> = emptyList(),
)
