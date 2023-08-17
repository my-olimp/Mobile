package ramble.sokol.myolimp.feature_calendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel

@Dao
interface PlansDao {

    @Upsert
    suspend fun addPlan(plan: PlanModel)

    @Update
    suspend fun updatePlan(plan: PlanModel)

    @Delete
    suspend fun deletePlan(plan: PlanModel)

    // unnecessary to use coroutines because it get live data
    @Query("SELECT * FROM plans")
    fun getPlans(): Flow<List<PlanModel>>

}
