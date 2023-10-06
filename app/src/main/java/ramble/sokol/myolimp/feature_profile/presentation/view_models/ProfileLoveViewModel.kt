package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.events.ProfileLoveEvent
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileLoveRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileLoveState

class ProfileLoveViewModel(context: Context) : ViewModel() {

    companion object {
        const val TAG = "ViewModelProfileLove"
        const val LOVE = 1
    }

    private val repository = ProfileLoveRepository()

    private val userDatabase: UserDatabase = UserDatabase(context)
    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    private val _state = MutableStateFlow(ProfileLoveState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            extractArticles()
            val subjects = libraryRepository.getUserSubjects()
            _state.update {
                it.copy(
                    userSubjects = subjects,
                    subjects = subjects.associateWith { false } as HashMap<String, Boolean>,
                    isSubjectLoaded = true
                )
            }
        }
    }

    fun onEvent(event: ProfileLoveEvent) {
        when(event) {
            is ProfileLoveEvent.OnChooseCheckbox -> {

                _state.update {
                    it.copy(
                      subjects = state.value.subjects.mapValues {item ->
                          if(item.key == event.subject) !item.value else item.value
                      } as HashMap<String, Boolean>
                    )
                }
            }
            is ProfileLoveEvent.OnFilterSubjects -> {
                _state.update {
                    it.copy(
                        filteredSubjects = state.value.userSubjects.filter { subject ->
                            state.value.subjects[subject] ?: false
                        }
                    )
                }
            }
            is ProfileLoveEvent.OnQueryUpdate -> {
                _state.update {
                    it.copy(queryText = event.query)
                }
            }
        }
    }
    private fun extractArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.extractArticles(1,LOVE,state.value.queryText).body()
                Log.i(TAG, "extract response is: $response")

                if(response != null) {
                    _state.update {
                        it.copy(
                            listArticles = response.toArticlesList(),
                            isArticlesLoaded = true
                        )
                    }
                }

            } catch (e: Exception) {
                Log.i(TAG,"extract throwed ${e.message}")
            }
        }
    }

}