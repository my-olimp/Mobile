package ramble.sokol.myolimp.feature_calendar.domain.repositories

import ramble.sokol.myolimp.feature_calendar.data.database.PlansDatabase
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel

class PlansRepository (
    private val database: PlansDatabase
) {
    fun getPlans()
        = database.getPlansDao().getPlans()

    suspend fun addPlan(plan: PlanModel)
        = database.getPlansDao().addPlan(plan)

    suspend fun updatePlan(plan: PlanModel)
            = database.getPlansDao().updatePlan(plan)

    suspend fun deletePlan(plan: PlanModel)
            = database.getPlansDao().deletePlan(plan)
}
