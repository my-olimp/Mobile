package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileLoveRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileLoveState

class ProfileLoveViewModel : ViewModel() {

    companion object {
        const val TAG = "ViewModelProfileLove"
        const val LOVE = 1
    }

    private val repository = ProfileLoveRepository()

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(ProfileLoveState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            extractArticles()
            extractSubjects()
        }
    }

    private fun extractSubjects() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.extractSubjects(
                    auth = dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()
                    ?: throw Exception("No access token")
                ).body()

                Log.i(TAG,"extracted subjects response is: $response")

                if(response != null) {
                    _state.update {
                        it.copy( subjects = response )
                    }
                }

            } catch (e: Exception) {
                Log.i(TAG,"extracting subjects throwed ${e.message}")
            }
        }
    }


    private fun extractArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.extractArticles(1,0/*LOVE*/,"").body() //have no loved articles
                Log.i(TAG, "extract response is: $response")

                if(response != null) {
                    _state.update {
                        it.copy(
                            listArticles = response.toArticlesList()
                        )
                    }
                }

            } catch (e: Exception) {
                Log.i(TAG,"extract throwed ${e.message}")
            }
        }
    }

}