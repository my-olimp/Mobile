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
        when(event) {
            is ArticleEvent.OnCheckAnswer -> {
                when(event.answer == state.value.article.blocks[event.blockId].questions[event.taskNum].answer) {
                    true -> {
                        val map = state.value.answers
                        map[event.taskId]?.let {
                            it.isError = false
                            it.isSuccess = true
                        }
                        _state.update {
                            it.copy(answers = map)
                        }
                    }
                    false -> {
                        val map = state.value.answers
                        map[event.taskId]?.let {
                            it.isError = true
                            it.isSuccess = false
                        }
                        _state.update {
                            it.copy(answers = map)
                        }
                    }
                }
            }
        }
    }

    fun fetchArticle(id: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.extractArticleById(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN)
                        ?: throw Exception("No access token"),
                    id = id /*TODO replace this*/,
                    onResult = { article ->
                        Log.i(TAG, "response article is : $article")
                        if (article != null) {
                            _state.update {
                                it.copy(article = article.asArticleModel())
                            }
                            Log.i(TAG,"new article model is: ${_state.value.article}")
                        }
                    },
                    onError = {
                        Log.i(TAG, "response with exception")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG,"request isn't sent with ${e.message}")
            }
        }
    }
}