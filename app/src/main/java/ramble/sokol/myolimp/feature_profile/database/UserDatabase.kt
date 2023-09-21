package ramble.sokol.myolimp.feature_profile.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

@Database(
    entities = [LocalUserModel::class],
    version = 4,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao() : UserDao

    companion object {

        @Volatile
        private var instance: UserDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_db.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }

}