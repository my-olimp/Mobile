package ramble.sokol.myolimp.feature_profile.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedArticleModel (
    @PrimaryKey val id: Int = -1,
    val views: Int = -1,
    val title: String = "",
    val authorModel: String = "",
    val createdAt: String = "",
    val subject: String = "",
    val isFavourite: Boolean = false,
    @Embedded val listTags: List<String> = emptyList(),
)
