package ramble.sokol.myolimp.feature_calendar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "plans"
)
data class PlanModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val subject: String,
    val color: String,
    val type: String,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val isFavourite: Boolean,
    val isCompleted: Boolean,
) : Serializable
