package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_profile.domain.events.ProfileLoveEvent
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileLoveRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileLoveState
import ramble.sokol.myolimp.utils.BaseViewModel

class ProfileLoveViewModel : BaseViewModel<ProfileLoveState>(ProfileLoveState()){

    companion object {
        const val TAG = "ViewModelProfileLove"
        const val LOVE = 1
    }

    private val repository = ProfileLoveRepository()

    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)


    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            extractArticles()
        }
    }

    init {
        updateLoader(true)
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
            if(state.value.isArticlesLoaded)updateLoader(false)
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

                timer.cancel()
                timer.start()
            }
            is ProfileLoveEvent.OnEmptyQuery -> {
                _state.update {
                    it.copy(
                        queryText = ""
                    )
                }
                timer.cancel()
                extractArticles()
            }
        }
    }

    private fun extractArticles() {
        _state.update {
            it.copy(
                isArticlesLoaded = false
            )
        }
        launchIO {
            val response = repository.extractArticles(
                page = 1,
                love = LOVE,
                query = state.value.queryText
            ).body()

            Log.i(TAG, "extract response is: $response")

            if (response != null) {
                _state.update {
                    it.copy(
                        listArticles = response.toArticlesList(),
                        isArticlesLoaded = true
                    )
                }
                if(state.value.isSubjectLoaded)updateLoader(false)
            }

        }
    }

}