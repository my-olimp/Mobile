package ramble.sokol.myolimp.feature_library.presenation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_library.presenation.LibraryEvent
import ramble.sokol.myolimp.feature_library.presenation.LibraryState

class LibraryViewModel : ViewModel() {

//    companion object {
//        private const val TAG = "ViewModelLibrary"
//    }

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private val repository = LibraryRepositoryImpl()

    init {
        viewModelScope.launch {
            _state.update { curValue ->
                curValue.copy(
                    isLoading = true
                )
            }
            _state.update { curValue ->
                curValue.copy(
                    articles = repository.getArticles(
                        auth = dataStore.getToken(CodeDataStore.ACCESS_TOKEN).first()
                            ?: throw Exception("No access token")
                    ),
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
