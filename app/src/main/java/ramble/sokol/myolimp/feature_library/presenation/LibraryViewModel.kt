package ramble.sokol.myolimp.feature_library.presenation

import android.content.Context
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

    private val userDatabase : UserDatabase = UserDatabase(context)
    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)


    init {
        viewModelScope.launch {
            _state.update { curValue ->
                curValue.copy(
                    isLoading = true
                )
            }
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
        }
    }
}
