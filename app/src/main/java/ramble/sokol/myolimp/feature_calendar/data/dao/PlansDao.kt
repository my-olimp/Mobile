package ramble.sokol.myolimp.feature_calendar.data.dao

import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlansDao {

    @Upsert
    suspend fun addPlan(plan: PlanModel)

    @Delete
    suspend fun deletePlan(plan: PlanModel)

    // unnecessary to use coroutines because it get live data
    @Query("SELECT * FROM plans")
    fun getPlans(): Flow<List<PlanModel>>

}
