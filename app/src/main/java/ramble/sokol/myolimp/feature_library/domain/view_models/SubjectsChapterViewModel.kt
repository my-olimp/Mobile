package ramble.sokol.myolimp.feature_library.domain.view_models

import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_library.domain.events.ChapterEvent
import ramble.sokol.myolimp.feature_library.domain.repositories.ChapterRepository
import ramble.sokol.myolimp.feature_library.domain.states.ChapterState
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions

class SubjectsChapterViewModel : BaseViewModel<ChapterState>(ChapterState())  {

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
        updateLoader(true)
        launchIO {
            chapterRepository.getArticles(
                subject = state.value.subject ?: throw ViewModelExceptions.Network,
                page = state.value.currentPage,
                isFavourites = state.value.isShowingFavourites,
                query = state.value.searchQuery ?: "",
                onSuccess = { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = result.articles.map { articles ->
                                articles.toArticleModel()
                            },
                            currentPage = result.currentPage,
                            amountOfPages = result.pageCount,
                        )
                    }
                    Log.i(TAG, "articles - ${state.value.articles}")
                },
                onError = { error ->
                    Log.i(TAG, "error occurred - $error")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }
}
