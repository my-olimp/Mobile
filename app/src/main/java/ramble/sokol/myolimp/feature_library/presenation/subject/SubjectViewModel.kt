package ramble.sokol.myolimp.feature_library.presenation.subject

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

class SubjectViewModel(context: Context) : ViewModel() {


    private val _state = MutableStateFlow(SubjectState())
    val state = _state.asStateFlow()

    private val userDatabase: UserDatabase = UserDatabase(context)

    init {
        _state.update { curValue ->
            curValue.copy(
                isLoading = true
            )
        }
    }

    fun onEvent(event: SubjectEvent) {
        viewModelScope.launch {
            when (event) {
                is SubjectEvent.OnLoadSubjectContent -> {
                    _state.update { curValue ->
                        curValue.copy(
                            subjectName = tempGetSubjectByID(event.subjectID)
                        )
                    }
                }
                is SubjectEvent.OnFilterFromBottomSheet -> TODO()
                is SubjectEvent.OnSearchQueryUpdated -> TODO()
                is SubjectEvent.OnShowFavourites -> TODO()
            }
        }
    }

    private fun tempGetSubjectByID(subjectID: Long) = "Математика"
}
