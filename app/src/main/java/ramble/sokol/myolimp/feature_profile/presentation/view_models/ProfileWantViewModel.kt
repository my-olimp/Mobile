package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_library.data.repository.LibraryRepositoryImpl
import ramble.sokol.myolimp.feature_profile.domain.states.WantToWatchState
import ramble.sokol.myolimp.utils.BaseViewModel

class ProfileWantViewModel : BaseViewModel<WantToWatchState>(WantToWatchState()) {

    private val libraryRepository = LibraryRepositoryImpl(database = userDatabase)

    private val timer = object: CountDownTimer(2000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            extractSavedArticles()
        }
    }

    init {
        updateLoader(true)
        viewModelScope.launch {
            extractSavedArticles()
            val subjects = libraryRepository.getUserSubjects()
            _state.update {
                it.copy(
                    userSubjects = subjects,
                    subjects = subjects.associateWith { false } as HashMap<String, Boolean>,
                )
            }
        }
    }

    private fun extractSavedArticles() {
        updateLoader(true)

        launchIO {
            val savedArticles = userRepository.getUser().first().savedArticles

            _state.update {
                it.copy(
                    savedArticles = savedArticles
                )
            }

            Log.i(TAG, "articles - ${state.value.savedArticles}")

            updateLoader(false)
        }
    }
}
