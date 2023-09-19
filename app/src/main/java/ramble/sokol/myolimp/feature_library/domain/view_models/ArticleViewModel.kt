package ramble.sokol.myolimp.feature_library.domain.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.repositories.LibraryRepository
import ramble.sokol.myolimp.feature_library.domain.states.ArticleState
import ramble.sokol.myolimp.feature_profile.data.Constants

class ArticleViewModel: ViewModel() {

    companion object {
        const val TAG = "ViewModelArticleLibrary"
    }

    private val dataStore = CodeDataStore()

    private val repository = LibraryRepository()

    private val _state = MutableStateFlow(ArticleState())
    val state = _state.asStateFlow()


    fun onEvent(
        event: ArticleEvent
    ) {
        /*TODO*/
    }

    fun fetchArticle() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.extractArticleById(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN)
                        ?: throw Exception("No access token"),
                    id = 1 /*TODO replace this*/,
                    onResult = { article ->
                        Log.i(TAG, "response article is : $article")
                        if (article != null) {
                            _state.update {
                                it.copy(article = article)
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG, "response with exception")
                    }
                )
            } catch (_: Exception) {}
        }
    }
}