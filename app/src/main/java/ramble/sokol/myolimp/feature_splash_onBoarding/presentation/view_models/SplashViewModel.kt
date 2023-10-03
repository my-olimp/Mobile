package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.LocalUserModel
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.states.LocalUserResult

class SplashViewModel : ViewModel() {

    companion object {
        const val TAG = "ViewModelSplash"
    }

    private val dataStore = CodeDataStore()
    private val apiRepository = ProfileRepository()

    private var _state = MutableStateFlow<LocalUserResult<LocalUserModel>>(LocalUserResult.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val token = dataStore.getToken(CodeDataStore.COOKIES).first()
                    ?: throw Exception("no cookie token")

                Log.i(
                    LocalUserViewModel.TAG, "refresh - $token / access - ${
                        dataStore.getToken(
                            CodeDataStore.ACCESS_TOKEN
                        ).first()
                    }"
                )

                val response = apiRepository.refreshToken(
                    cookie = token,
                )

                if (response.isSuccessful) {
                    Log.i(TAG, "user - ${response.body()?.user}")

                    LocalUserViewModel().saveUser(response.body()?.user ?: throw Exception("empty user body / local"))

                    _state.value = LocalUserResult.Success(
                        response.body()?.user ?: throw Exception("empty user body")
                    )
                } else {
                    _state.value = LocalUserResult.Error(response.message())
                }
            } catch (ex : Exception) {
                Log.i(TAG, "exception - ${ex.message}")
                _state.value = LocalUserResult.Error(ex.message)
            }
        }
    }
}
