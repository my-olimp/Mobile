package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigator
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.RegisterImageScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_authentication.data.models.SubjectModel
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

                Log.i(TAG, "chosen subjects - ${state.value.chosenSubjects}")
            }
            is RegisterSubjectEvent.OnLoadSubjects -> {
                getSubjects()
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

    private fun updateUserData(
        navigator: DestinationsNavigator
    ) {
        viewModelScope.launch {

            val chosenSubjectsIds = state.value.chosenSubjects.map {
                it.id
            }

            Log.i(TAG, "ids - $chosenSubjectsIds")

            repository.updateSubjects(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN)?: throw Exception("No access token"),
                subjects = RequestSubjects(chosenSubjectsIds),
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
        // get subjects
        viewModelScope.launch {
            repository.getSchools(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN) ?: throw Exception("No access token"),
                onResult = { subjects->
                    if (subjects != null) {

                        val subjectsWithIcon = mutableListOf<SubjectModel>()

                        subjects.forEach {
                            when (it.name) {
                                "Информатика" -> {
                                    subjectsWithIcon.add(SubjectModel(
                                        id = it.id,
                                        name = it.name,
                                        icon = R.drawable.ic_profile_subject_it
                                    ))
                                }
                                "Математика" -> {
                                    subjectsWithIcon.add(SubjectModel(
                                        id = it.id,
                                        name = it.name,
                                        icon = R.drawable.ic_profile_subject_math
                                    ))
                                }
                                "Физика" -> {
                                    subjectsWithIcon.add(SubjectModel(
                                        id = it.id,
                                        name = it.name,
                                        icon = R.drawable.ic_subject_phycics
                                    ))
                                }
                                "Литература" -> {
                                    subjectsWithIcon.add(SubjectModel(
                                        id = it.id,
                                        name = it.name,
                                        icon = R.drawable.ic_subject_literature
                                    ))
                                }
                                "Русский Язык" -> {
                                    subjectsWithIcon.add(SubjectModel(
                                        id = it.id,
                                        name = it.name,
                                        icon = R.drawable.ic_subject_russian
                                    ))
                                }
                            }
                        }

                        _state.update {
                            it.copy(
                                subjects = subjectsWithIcon
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