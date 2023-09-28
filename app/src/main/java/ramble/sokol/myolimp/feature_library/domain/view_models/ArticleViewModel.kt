package ramble.sokol.myolimp.feature_library.domain.view_models

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
import ramble.sokol.myolimp.feature_library.data.models.RequestAnswerModel
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.repositories.LibraryRepository
import ramble.sokol.myolimp.feature_library.domain.states.ArticleState
import ramble.sokol.myolimp.feature_library.domain.states.TaskState


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
                    checkAnswer(taskId = event.taskId, blockId = event.blockId )
            }
            is ArticleEvent.OnAnswerTyped -> {
                val map = state.value.answers
                if(map[event.taskId] == null){
                    map[event.taskId] = TaskState(answer = event.answer)
                } else {
                    map[event.taskId]?.let {
                        it.answer = event.answer
                        it.isError = false
                        it.isSuccess = false
                    }
                }
                _state.update {
                    it.copy(
                        answers = map,
                        status = !state.value.status
                    )
                }
                Log.i(TAG,"state updated: ${_state.value.answers[event.taskId]}")
            }
        }
    }

    fun fetchArticle(id: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.extractArticleById(
                    auth = dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()
                        ?: throw Exception("No access token"),
                    id = id,
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

    private fun checkAnswer(taskId: Int,blockId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.checkAnswer(
                    auth = dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first() ?: throw Exception("no access token"),
                    id = blockId,
                    body = RequestAnswerModel(
                        id = taskId,
                        answer = state.value.answers[taskId]?.answer ?: ""
                    ),
                    onResult = {
                        Log.i(TAG,"response body $it")
                        if(it != null) {
                            val map = state.value.answers
                            when(it.isCorrect) {
                                false -> {
                                    map[taskId]?.let { taskState ->
                                        taskState.isError = true
                                        taskState.isSuccess = false
                                    }
                                    _state.update { state ->
                                        state.copy(
                                            answers = map,
                                            status = !state.status
                                        )
                                    }
                                }
                                true -> {
                                    map[taskId]?.let { taskState ->
                                        taskState.isError = false
                                        taskState.isSuccess = true
                                    }
                                    _state.update { state ->
                                        state.copy(
                                            answers = map,
                                            status = !state.status
                                        )
                                    }
                                }
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG,"response error ${it.message}")
                    }
                )
            } catch(e: Exception) {
                Log.i(TAG,"request cast ${e.message}")
            }
        }
    }
}