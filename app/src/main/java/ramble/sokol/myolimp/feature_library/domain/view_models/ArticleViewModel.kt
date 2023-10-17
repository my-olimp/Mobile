package ramble.sokol.myolimp.feature_library.domain.view_models

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_library.data.models.RequestAnswerModel
import ramble.sokol.myolimp.feature_library.domain.events.ArticleEvent
import ramble.sokol.myolimp.feature_library.domain.repositories.LibraryRepository
import ramble.sokol.myolimp.feature_library.domain.states.ArticleState
import ramble.sokol.myolimp.feature_library.domain.states.TaskState
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions


class ArticleViewModel: BaseViewModel<ArticleState>(ArticleState()) {

    private val repository = LibraryRepository()

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

            is ArticleEvent.OnFavourites -> {
                launchIO {
                    updateLoader(true)

                    repository.addToFavourites(
                        id = state.value.article.id,
                        onResult = { result->
                            Log.i(TAG, "result - $result")

                            if (result != null) {
                                _state.update {
                                    it.copy(
                                        article = it.article.copy(isFavourite = result.isFavourite)
                                    )
                                }
                            } else {
                                Log.i(TAG, "null body")
                            }

                            updateLoader(false)
                        },
                        onError = {
                            Log.i(TAG, "Error occurred - $it")
                            castError(ViewModelExceptions.Network)
                        }
                    )
                }
            }

            is ArticleEvent.OnFetchArticle -> {
                fetchArticle(event.id)
            }
        }
    }

    private fun fetchArticle(id: Int = 1) {
        launchIO {
            updateLoader(true)

            repository.extractArticleById(
                id = id,
                onResult = { article ->
                    Log.i(TAG, "response article is : $article")
                    if (article != null) {
                        _state.update {
                            it.copy(article = article.asArticleModel())
                        }
                        Log.i(TAG,"new article model is: ${_state.value.article}")
                    }

                    updateLoader(false)
                },
                onError = {
                    Log.i(TAG, "response with exception")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

    private fun checkAnswer(taskId: Int,blockId: Int) {
        launchIO {
            updateLoader(true)
            repository.checkAnswer(
                auth = datastore.getToken(CodeDataStore.ACCESS_TOKEN).first()
                    ?: throw ViewModelExceptions.Network,
                id = blockId,
                body = RequestAnswerModel(
                    id = taskId,
                    answer = state.value.answers[taskId]?.answer ?: ""
                ),
                onResult = {
                    Log.i(TAG, "response body $it")
                    if (it != null) {
                        val map = state.value.answers
                        when (it.isCorrect) {
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

                    updateLoader(false)
                },
                onError = {
                    Log.i(TAG, "response error ${it.message}")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

}