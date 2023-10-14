package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import ramble.sokol.myolimp.destinations.RegisterImageScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_authentication.domain.events.RegisterSubjectEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegisterSubjectsRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterSubjectsState
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import ramble.sokol.myolimp.utils.OlimpViewModel

class RegisterSubjectsViewModel : OlimpViewModel<RegisterSubjectsState>(RegisterSubjectsState()) {

    companion object {
        const val TAG = "ViewModelRegisterSubjects"
    }

    private val repository = RegisterSubjectsRepository()

    init {
        updateLoader(true)
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
                updateUserData(
                    event.navigator,
                    event.isWork
                )
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
        navigator: DestinationsNavigator,
        isWorkScreen: Boolean = false
    ) {
        updateLoader(true)
        launchIO {
            repository.updateSubjects(
                subjects = RequestSubjects(state.value.chosenSubjects),
                onResult = {
                    runBlocking {
                        if (it != null) {
                            updateDatabase(it)
                            navigator.navigate(RegisterImageScreenDestination(isWorkScreen = isWorkScreen))
                        }
                        updateLoader(false)
                        Log.i(TAG, "result - $it")
                    }
                },
                onError = {
                    Log.i(TAG, "error - $it")
                    castError()
                }
            )
        }
    }

    private fun getSubjects() {
        launchIO {
            repository.getSubjects(
                onResult = { subjects->
                    if (subjects != null) {
                        _state.update {
                            it.copy(
                                subjects = subjects
                            )
                        }
                        updateLoader(false)
                    }
                    Log.i(TAG, "success - $subjects")
                },
                onError = {
                    Log.i(TAG, "error - $it")
                    castError()
                }
            )
        }
    }

    private suspend fun updateDatabase(response: ResponseUserModel) {
        userRepository.deleteUsers()

        userRepository.saveUser(
            user = response.toLocalUserModel()
        )
    }
}
