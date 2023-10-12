package ramble.sokol.myolimp.feature_profile.domain.repositories

import android.content.Context
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

class LocalUserRepository (
    private val database: UserDatabase
) {

    fun getUser()
            = database.getUserDao().getUser()

//    fun getUserById(id: String) {
//        database.getUserDao().getUserById(id = id)
//    }

    suspend fun saveUser(user: LocalUserModel)
            = database.getUserDao().saveUser(user = user)

    suspend fun updateUser(user: LocalUserModel)
            = database.getUserDao().updateUser(user = user)

//    suspend fun deleteUser(id: String)
//            = database.getUserDao().deleteUser(id = id)

    suspend fun deleteUsers()
        = database.getUserDao().deleteAllUsers()

}
