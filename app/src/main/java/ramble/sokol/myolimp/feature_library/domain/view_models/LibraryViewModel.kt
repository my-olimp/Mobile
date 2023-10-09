package ramble.sokol.myolimp.feature_library.domain.view_models

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryEvent
import ramble.sokol.myolimp.feature_library.presenation.mainScreen.LibraryState
import ramble.sokol.myolimp.feature_profile.database.UserDatabase

class LibraryViewModel(context: Context) : ViewModel() {

    companion object {
        private const val TAG = "ViewModelLibrary"
    }

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private val userDatabase: UserDatabase = UserDatabase(context)
    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            searchArticles()
        }
    }

    init {
        viewModelScope.launch {
            _state.update { curValue ->
                curValue.copy(
                    isLoading = true
                )
            }

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
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
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
                            isError = true
                        )
                    }
                }
            )
        }
    }
}
