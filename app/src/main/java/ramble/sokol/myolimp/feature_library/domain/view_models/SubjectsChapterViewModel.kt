package ramble.sokol.myolimp.feature_library.domain.view_models

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_library.domain.events.ChapterEvent
import ramble.sokol.myolimp.feature_library.domain.repositories.ChapterRepository
import ramble.sokol.myolimp.feature_library.domain.states.ChapterState

class SubjectsChapterViewModel : ViewModel()  {

    companion object {
        private const val TAG = "ViewModelSubjectsChapter"
    }

    private val _state = MutableStateFlow(ChapterState())
    val state = _state.asStateFlow()

    private val chapterRepository = ChapterRepository()

    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            searchArticles()
        }
    }

    fun onEvent(
        event: ChapterEvent
    ) {
        when (event) {
            is ChapterEvent.OnSearchArticlesBySubject -> {
                _state.update {
                    it.copy(
                        subject = event.subject
                    )
                }
                searchArticles()
            }

            ChapterEvent.OnEmptyQuery -> {
                _state.update {
                    it.copy(
                        searchQuery = null
                    )
                }
                timer.cancel()
            }

            is ChapterEvent.OnSearchQueryUpdated -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                timer.cancel()
                timer.start()
            }

            is ChapterEvent.OnShowFavourites -> {
                _state.update {
                    it.copy(
                        isShowingFavourites = event.isShowing
                    )
                }
                searchArticles()
            }
        }
    }

    private fun searchArticles() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        try {
            viewModelScope.launch {
                chapterRepository.getArticles(
                    subject = state.value.subject ?: throw Exception("no subject"),
                    page = state.value.currentPage,
                    isFavourites = state.value.isShowingFavourites,
                    query = state.value.searchQuery ?: "",
                    onSuccess = { result->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                articles = result.articles.map { articles->
                                    articles.toArticleModel()
                                },
                                currentPage = result.currentPage,
                                amountOfPages = result.pageCount,
                            )
                        }
                        Log.i(TAG, "articles - ${state.value.articles}")
                    },
                    onError = { error->

                        Log.i(TAG, "error occurred - $error")

                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                )
            }
        } catch (ex: Exception) {
            Log.i(TAG, "ex - ${ex.message}")
        }
    }
}
