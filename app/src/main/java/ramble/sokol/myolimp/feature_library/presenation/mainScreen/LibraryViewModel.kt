package ramble.sokol.myolimp.feature_library.presenation.mainScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_profile.database.UserDatabase

class LibraryViewModel(context: Context) : ViewModel() {

//    companion object {
//        private const val TAG = "ViewModelLibrary"
//    }

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private val userDatabase: UserDatabase = UserDatabase(context)
    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    companion object {
        private const val TAG = "ViewModelLibrary"
    }


    init {
        viewModelScope.launch {
            _state.update { curValue ->
                curValue.copy(
                    isLoading = true
                )
            }
            try {
                _state.update { curValue ->
                    curValue.copy(
                        articles = libraryRepository.getArticles(
                            auth = dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()
                                ?: throw Exception("No access token")
                        ),
                        userSubjects = libraryRepository.getUserSubjects(),
                        isLoading = false
                    )
                }
                _state.update { curValue ->
                    curValue.copy(
                        bottomSheetSubjectsMap = curValue.userSubjects.associateWith { false }
                    )
                }
            } catch (e: Exception) {
                Log.i(TAG, "init ex: ${e.message}")
                _state.update { curValue ->
                    curValue.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            } finally {

            }
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
                // TODO search
            }

            is LibraryEvent.OnShowFavourites -> {
                _state.update {
                    it.copy(
                        isShowingFavourites = event.isShowing
                    )
                }
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
}
