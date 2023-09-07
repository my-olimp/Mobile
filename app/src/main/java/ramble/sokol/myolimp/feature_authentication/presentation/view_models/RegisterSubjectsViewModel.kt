package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.events.RegisterSubjectEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegisterSubjectsRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterSubjectsState
import ramble.sokol.myolimp.feature_profile.data.Constants

class RegisterSubjectsViewModel (
    context: Context
) : ViewModel() {

    companion object {
        const val TAG = "ViewModelRegisterSubjects"
    }

    private val repository = RegisterSubjectsRepository(context = context)
    private val dataStore = CodeDataStore(context = context)

    private val _state = MutableStateFlow(RegisterSubjectsState())
    val state = _state.asStateFlow()

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
            }
        }
    }

    fun getSubjects() {
        // get subjects
        viewModelScope.launch {
            repository.getSchools(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN) ?: throw Exception("No access token"),
                onResult = {
                    Log.i(TAG, "success - $it")
                },
                onError = {
                    // TODO PARSE ERROR
                    Log.i(TAG, "error - $it")
                }
            )
        }
    }

}