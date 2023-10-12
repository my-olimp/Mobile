package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.destinations.RegisterImageScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_authentication.domain.events.RegisterSubjectEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegisterSubjectsRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterSubjectsState
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository

class RegisterSubjectsViewModel : ViewModel(), KoinComponent {

    companion object {
        const val TAG = "ViewModelRegisterSubjects"
    }

    private val context by inject<Context>()

    private val repository = RegisterSubjectsRepository()

    private val _state = MutableStateFlow(RegisterSubjectsState())
    val state = _state.asStateFlow()

    private val userDatabase : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = userDatabase)

    init {
        updateLoading(true)
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

        updateLoading(true)

        viewModelScope.launch {

            repository.updateSubjects(
                subjects = RequestSubjects(state.value.chosenSubjects),
                onResult = {
                    runBlocking {
                        if (it != null) {
                            updateDatabase(it)

                            navigator.navigate(RegisterImageScreenDestination(isWorkScreen = isWorkScreen))
                        }

                        updateLoading(false)

                        Log.i(TAG, "result - $it")
                    }
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
                        updateLoading(false)
                    }

                    Log.i(TAG, "success - $subjects")
                },
                onError = {
                    Log.i(TAG, "error - $it")
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

    private fun updateLoading(isLoading: Boolean) = _state.update {
        it.copy(
            isLoading = isLoading
        )
    }
}
