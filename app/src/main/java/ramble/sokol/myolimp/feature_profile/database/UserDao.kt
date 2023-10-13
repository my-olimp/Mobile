package ramble.sokol.myolimp.feature_profile.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: LocalUserModel)

    @Update
    suspend fun updateUser(user: LocalUserModel)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: String)

//    @Delete
//    suspend fun deleteUser(userModel: LocalUserModel)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): Flow<LocalUserModel>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: String): Flow<LocalUserModel>

    @Query("SELECT subjects FROM user LIMIT 1")
    suspend fun getUserSubjects(): String

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}
