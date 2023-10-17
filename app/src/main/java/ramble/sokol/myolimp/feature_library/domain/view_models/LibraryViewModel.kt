package ramble.sokol.myolimp.feature_library.domain.view_models

import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryEvent
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryState
import ramble.sokol.myolimp.utils.BaseViewModel

class LibraryViewModel : BaseViewModel<LibraryState>(LibraryState()) {

    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            searchArticles()
        }
    }

    init {
        launchIO {
            updateLoader(true)

            val subjects = libraryRepository.getUserSubjects()

            _state.update {
                it.copy(
                    userSubjects = subjects,
                    bottomSheetSubjectsMap = subjects.associateWith { false }
                )
            }

            searchArticles()
        }
    }

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnSearchQueryUpdated -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                timer.cancel()
                timer.start()
            }

            is LibraryEvent.OnEmptyQuery -> {
                _state.update {
                    it.copy(
                        searchQuery = ""
                    )
                }
                timer.cancel()
            }

            is LibraryEvent.OnShowFavourites -> {
                _state.update {
                    it.copy(
                        isShowingFavourites = event.isShowing
                    )
                }
                searchArticles()
            }

            is LibraryEvent.OnCheckboxSubject -> {
                _state.update { libraryState ->
                    libraryState.copy(
                        bottomSheetSubjectsMap = libraryState.bottomSheetSubjectsMap.mapValues {
                            if (it.key == event.subjectName) it.value.not() else it.value
                        },
                    )
                }
            }

            is LibraryEvent.OnFilterSubjectFromBottomSheet -> {
                _state.update { libraryState ->
                    libraryState.copy(
                        filteredSubjects = state.value.userSubjects.filter { subject ->
                            state.value.bottomSheetSubjectsMap[subject] ?: false
                        },
                    )
                }
            }
        }
    }

    private fun searchArticles() {
        updateLoader(true)

        launchIO {
            libraryRepository.getArticles(
                page = state.value.currentPage,
                isShowFavourites = state.value.isShowingFavourites,
                query = state.value.searchQuery,
                onSuccess = { articles->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = articles
                        )
                    }
                },
                onError = { error->

                    Log.i(TAG, "error occurred - $error")

                    _state.update {
                        it.copy(
                            isLoading = false,
                            isNetworkError = true
                        )
                    }
                }
            )
        }
    }
}
