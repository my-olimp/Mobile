package ramble.sokol.myolimp.feature_profile.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserModel

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: LocalUserModel)

    @Update
    suspend fun updateUser(user: LocalUserModel)

    @Delete
    suspend fun deleteUser(user: LocalUserModel)

    @Query("SELECT * FROM user")
    fun getUser(): Flow<LocalUserModel>
}
