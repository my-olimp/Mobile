package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.states.SplashState
import ramble.sokol.myolimp.utils.BaseViewModel

class SplashViewModel : BaseViewModel<SplashState>(SplashState()) {

    private val dataStore = CodeDataStore()
    private val apiRepository = ProfileRepository()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                val token = dataStore.getToken(CodeDataStore.COOKIES).first()
                    ?: throw Exception("no cookie token")

                val response = apiRepository.refreshToken(
                    cookie = token,
                )

                if (response.isSuccessful) {
                    Log.i(TAG, "user - ${response.body()?.user}")

                    val previousData = userRepository.getUser().first()

                    userRepository.deleteUsers()

                    val result = response.body()?.user ?: throw Exception("empty body")

                    // save user in local storage
                    userRepository.saveUser(
                        result.copy(
                            savedArticles = previousData.savedArticles
                        )
                    )

                    Log.i(TAG, "after user - ${userRepository.getUser().firstOrNull()}")

                    onSuccess()
                } else {
                    // delete user from local storage
                    userRepository.deleteUsers()

                    Log.i(TAG, "user - ${userRepository.getUser().firstOrNull()}")

                    onError()
                }
            } catch (ex : Exception) {
                Log.i(TAG, "exception - ${ex.message}")
                onError()
            }
        }
    }

    private fun onError() {
        _state.update {
            it.copy(
                isError = true
            )
        }
    }

    private fun onSuccess() {
        _state.update {
            it.copy(
                isSuccess = true
            )
        }
    }
}
