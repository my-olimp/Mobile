package ramble.sokol.myolimp.feature_profile.domain.repositories

import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

class LocalUserRepository (
    private val database: UserDatabase
) {
    fun getUser()
            = database.getUserDao().getUser()

    suspend fun saveUser(user: LocalUserModel)
            = database.getUserDao().saveUser(user = user)

    suspend fun updateUser(user: LocalUserModel)
            = database.getUserDao().updateUser(user = user)

    suspend fun deleteUser(user: LocalUserModel)
            = database.getUserDao().deleteUser(user = user)

}
