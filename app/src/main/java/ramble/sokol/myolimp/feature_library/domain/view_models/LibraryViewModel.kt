package ramble.sokol.myolimp.feature_library.domain.view_models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.feature_library.domain.events.LibraryEvent
import ramble.sokol.myolimp.feature_library.domain.states.LibraryState

class LibraryViewModel : ViewModel(){

//    companion object {
//        private const val TAG = "ViewModelLibrary"
//    }

    private val _state = MutableStateFlow(
        LibraryState()
    )

    val state = _state.asStateFlow()

    fun onEvent(
        event: LibraryEvent
    ) {
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
