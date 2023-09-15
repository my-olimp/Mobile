package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.utils.CookiesDataStore

class SplashViewModel (
    context: Context
) : ViewModel() {

    companion object {
        const val TAG = "ViewModelSplash"
    }

    private var database : UserDatabase = UserDatabase.invoke(context)
    private var repository : LocalUserRepository = LocalUserRepository(database = database)
    private val apiRepository = ProfileRepository()
    private val cookiesDataStore = CookiesDataStore()

    private var _user = MutableStateFlow(
        repository.getUser()
    )
    val user = _user.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            try {
                apiRepository.refreshToken (
                    cookie = cookiesDataStore.getCookies(Constants.COOKIES)
                        ?: throw Exception("no cookie token"),
                    onResult = {
                        if (it != null) {

                            Log.i(TAG, "result - $it")

                            runBlocking {
                                repository.saveUser(
                                    LocalUserModel(
                                        id = it.user.id ?: "",

                                        firstName = it.user.firstName ?: "",
                                        secondName = it.user.secondName ?: "",
                                        thirdName = it.user.thirdName ?: "",

                                        gender = it.user.gender ?: "",
                                        dateOfBirth = it.user.dateOfBirth ?: "",

                                        profileImg = "https://storage.yandexcloud.net/myolimp/user/avatar/${it.user.id}.webp",

                                        email = it.user.email ?: "",
                                        phone = it.user.phone ?: "",
                                        snils = it.user.snils ?: "",

                                        grade = it.user.grade ?: 0,
                                        accountType = it.user.accountType ?: "",

                                        region = (it.user.region
                                            ?: ResponseRegionModel()).asRegion(),
                                        city = (it.user.city ?: ResponseCityModel()).asCity(),
                                        school = (it.user.school
                                            ?: ResponseSchoolModel()).asSchool(),

                                        )
                                )

                                Log.i(TAG, "user - ${repository.getUser()}")
                            }

                        } else {
                            Log.i(TAG, "err 2")
                        }
                    },
                    onError = {
                        Log.i(TAG, "err - $it")
                    }
                )
            } catch (ex : Exception) {
                Log.i(TAG, "saveUser: $ex")
            }

        }
    }
}
