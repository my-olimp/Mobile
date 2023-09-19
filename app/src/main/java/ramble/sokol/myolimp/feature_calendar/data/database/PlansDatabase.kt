package ramble.sokol.myolimp.feature_calendar.data.database

import ramble.sokol.myolimp.feature_calendar.data.dao.PlansDao
import ramble.sokol.myolimp.feature_calendar.data.models.PlanModel
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Database(
    entities = [PlanModel::class],
    version = 12,
    exportSchema = false
)
abstract class PlansDatabase : RoomDatabase() {

    abstract fun getPlansDao() : PlansDao

    companion object {

        @Volatile
        private var instance: PlansDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PlansDatabase::class.java,
            "plans_db.db"
        )
        .fallbackToDestructiveMigration()
        .build()

    }
}