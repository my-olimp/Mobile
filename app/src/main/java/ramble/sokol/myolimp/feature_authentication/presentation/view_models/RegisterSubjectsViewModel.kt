package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.RegisterImageScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_authentication.domain.events.RegisterSubjectEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegisterSubjectsRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterSubjectsState

class RegisterSubjectsViewModel : ViewModel() {

    companion object {
        const val TAG = "ViewModelRegisterSubjects"
    }

    private val repository = RegisterSubjectsRepository()

    private val _state = MutableStateFlow(RegisterSubjectsState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                isLoading = false
            )
        }
        getSubjects()
    }

    fun onEvent(
        event: RegisterSubjectEvent
    ) {
        when (event) {
            is RegisterSubjectEvent.OnSubjectClicked -> {
                val chosenSubjects = _state.value.chosenSubjects

                if (!chosenSubjects.contains(event.subject)) chosenSubjects.add(event.subject)
                else chosenSubjects.remove(event.subject)

                _state.update {
                    it.copy(
                        chosenSubjects = chosenSubjects
                    )
                }

                checkAbilityToNext()

                Log.i(TAG, "chosen subjects - ${state.value.chosenSubjects}")
            }
            is RegisterSubjectEvent.OnSearchQueryUpdated -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query,
                    )
                }

                Log.i(TAG, "chosen subjects - ${state.value.searchQuery}")
            }
            is RegisterSubjectEvent.OnSearched -> {
                _state.update {
                    it.copy(
                        isSearching = event.isSearching
                    )
                }
            }
            is RegisterSubjectEvent.OnNext -> {
                updateUserData(event.navigator)
            }
        }
    }

    private fun checkAbilityToNext() {
        _state.update {
            it.copy(
                isNextEnabled = state.value.chosenSubjects.size > 0
            )
        }
    }

    private fun updateUserData(
        navigator: DestinationsNavigator
    ) {
        viewModelScope.launch {
            Log.i(TAG, "ids - ${state.value.chosenSubjects}")

            repository.updateSubjects(
                subjects = RequestSubjects(state.value.chosenSubjects),
                onResult = {
                    if (it != null) {
                        navigator.navigate(RegisterImageScreenDestination)
                    }

                    Log.i(TAG, "result - $it")
                },
                onError = {
                    Log.i(TAG, "error - $it")
                }
            )
        }
    }

    private fun getSubjects() {
        viewModelScope.launch {
            repository.getSubjects(
                onResult = { subjects->
                    if (subjects != null) {

                        _state.update {
                            it.copy(
                                subjects = subjects
                            )
                        }
                    }
                    Log.i(TAG, "success - $subjects")
                },
                onError = {
                    Log.i(TAG, "error - $it")
                }
            )
        }
    }
}
