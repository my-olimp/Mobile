package ramble.sokol.myolimp.feature_calendar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "plans"
)
data class PlanModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val subject: String,
    val color: String,
    val type: String,
    val startTime: String,
    val endTime: String,
    val isFavourite: Boolean,
)
