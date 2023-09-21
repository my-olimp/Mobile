package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel

class LocalUserViewModel : ViewModel(), KoinComponent {

    companion object {
        const val TAG = "ViewModelLocalUser"
    }

    private val context by inject<Context>()
    private var database : UserDatabase = UserDatabase.invoke(context)
    private var repository : LocalUserRepository = LocalUserRepository(database = database)
    private val apiRepository = ProfileRepository()
    private val dataStore = CodeDataStore()

    private var _user = MutableStateFlow(
        repository.getUser()
    )
    var user = _user.asStateFlow()

    var isError = mutableStateOf (
        false
    )

    fun getUser() {
        runBlocking {
            try {

                val token = dataStore.getToken(CodeDataStore.COOKIES).first()
                    ?: throw Exception("no cookie token")

                Log.i(TAG, "refresh - $token / access - ${dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()}")

                val response = apiRepository.refreshToken(
                    cookie = token,
                )

                Log.i(TAG, "response - ${response.body()}")

                if (response.body() != null) {
                    saveUser(response.body()?.user ?: throw Exception("empty user body"))
                } else {
                    isError.value = true
                }

            } catch (ex : Exception) {
                Log.i(TAG, "saveUser: $ex")
            }
        }
    }

    fun saveUser(
        user: LocalUserModel
    ) {
        runBlocking {
            repository.saveUser(user)

            Log.i(TAG, "user - ${repository.getUser().first()}")
        }
    }
}