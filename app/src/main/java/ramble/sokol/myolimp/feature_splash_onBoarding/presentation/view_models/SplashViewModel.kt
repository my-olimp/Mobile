package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository

class SplashViewModel (
    context: Context
) : ViewModel() {

    companion object {
        const val TAG = "ViewModelSplash"
    }

    private var database : UserDatabase = UserDatabase.invoke(context)
    private var repository : LocalUserRepository = LocalUserRepository(database = database)

    private val _user  = repository.getUser()
    val user = mutableStateOf(_user)


    fun show() {
        Log.i(TAG, "user - ${user}")
    }
}
