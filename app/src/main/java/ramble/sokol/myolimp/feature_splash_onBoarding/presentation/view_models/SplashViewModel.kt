package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.states.LocalUserResult

class SplashViewModel : ViewModel(), KoinComponent {

    companion object {
        const val TAG = "ViewModelSplash"
    }

    private val context by inject<Context>()
    private var database : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = database)

    private val dataStore = CodeDataStore()
    private val apiRepository = ProfileRepository()

    private var _state = MutableStateFlow<LocalUserResult<LocalUserModel>>(LocalUserResult.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val token = dataStore.getToken(CodeDataStore.COOKIES).first()
                    ?: throw Exception("no cookie token")

                val response = apiRepository.refreshToken(
                    cookie = token,
                )

                if (response.isSuccessful) {
                    Log.i(TAG, "user - ${response.body()?.user}")

                    userRepository.deleteUsers()

                    Log.i(TAG, "before user - ${userRepository.getUser().firstOrNull()}")

                    // save user in local storage
                    userRepository.saveUser(response.body()?.user ?: throw Exception("empty body"))

                    Log.i(TAG, "after user - ${userRepository.getUser().firstOrNull()}")

                    _state.value = LocalUserResult.Success(
                        response.body()?.user ?: throw Exception("empty user body")
                    )
                } else {
                    try {
                        // delete user from local storage
                        userRepository.deleteUsers()

                        Log.i(TAG, "user - ${userRepository.getUser().firstOrNull()}")

                    } catch (ex: Exception) {
                        Log.i(TAG, "exception - $ex")
                    }

                    _state.value = LocalUserResult.Error(response.message())
                }
            } catch (ex : Exception) {
                Log.i(TAG, "exception - ${ex.message}")
                _state.value = LocalUserResult.Error(ex.message)
            }
        }
    }
}
