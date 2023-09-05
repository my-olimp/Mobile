package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.BeginAuthenticationScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.feature_profile.domain.models.UserModel
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.utils.CookiesDataStore

class ProfileViewModel (
    context: Context
) : ViewModel() {

    companion object {
        private const val TAG : String = "ViewModelProfile"
    }

    private val dataStore = CodeDataStore(context = context)
    private val cookiesDataStore = CookiesDataStore(context = context)

    private val repository = ProfileRepository(context = context)

    private val _state = MutableStateFlow(
        UserModel()
    )
    val state = _state.asStateFlow()

    fun onEvent (
        event: ProfileEvent
    ) {
        when (event) {
            is ProfileEvent.OnFirstNameChanged -> {
                _state.value = _state.value.copy(
                    firstName = event.firstName
                )
            }

            is ProfileEvent.OnSecondNameChanged -> {
                _state.value = _state.value.copy(
                    secondName = event.secondName
                )
            }

            is ProfileEvent.OnThirdNameChanged -> {
                _state.value = _state.value.copy(
                    thirdName = event.thirdName
                )
            }

            is ProfileEvent.OnDobChanged -> {
                _state.value = _state.value.copy(
                    dateOfBirth = event.dob
                )
            }

            is ProfileEvent.OnGenderChanged -> {
                _state.value = _state.value.copy(
                    gender = event.gender
                )
            }

            is ProfileEvent.OnSnilsChanged -> {
                _state.value = _state.value.copy(
                    snils = event.snils
                )
            }

            is ProfileEvent.OnImgChanged -> {
                _state.value = _state.value.copy(
                    profileImg = event.img
                )
            }

            is ProfileEvent.OnMarkerClicked -> {
                _state.value = _state.value.copy(
                    hasThird = event.hasThird
                )
            }

            is ProfileEvent.OnImgDelete -> {
                _state.value = _state.value.copy(
                    profileImg = null
                )
            }

            is ProfileEvent.OnSave -> {
                saveUserData()
                viewModelScope.launch {
                    updateUserData(user = _state.value)
                }
            }

            is ProfileEvent.OnImgSave -> {
                viewModelScope.launch {
                   updateUserImg()
                }
            }
            is ProfileEvent.OnRegionChanged -> {
                _state.value = _state.value.copy(
                    region = event.region
                )
            }

            is ProfileEvent.OnCityChanged -> {
                _state.value = _state.value.copy(
                    city = event.city
                )
            }

            is ProfileEvent.OnSchoolChanged -> {
                _state.value = _state.value.copy(
                    school = event.school
                )
            }

            is ProfileEvent.OnGradeChanged -> {
                _state.value = _state.value.copy(
                    grade = event.grade
                )
            }

            is ProfileEvent.OnPhoneChanged -> {
                _state.value = _state.value.copy(
                    phone = event.phone
                )
            }

            is ProfileEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }

            is ProfileEvent.OnLogOut -> {
                viewModelScope.launch {
                    dataStore.deleteToken()
                    cookiesDataStore.deleteCookies()

                    event.navigator.navigate(BeginAuthenticationScreenDestination)

                    try {
                        repository.logOut(
                            cookie=cookiesDataStore.getCookies(Constants.COOKIES)!!,
                            onResult = {
                                Log.i(TAG, "completed")
                            },
                            onError = {
                                Log.i(TAG, "error occurred - $it")
                            }
                        )
                    } catch (ex: Exception) {
                        Log.i(TAG, "exception - ${ex.message}")
                    }
                }
            }

            is ProfileEvent.OnRefreshToken -> {

                refreshToken()
            }
        }
    }

    private fun refreshToken()  {
        viewModelScope.launch {
            try {
                repository.refreshToken(
                    cookie=cookiesDataStore.getCookies(Constants.COOKIES)!!,
                    onResult = { result->
                        Log.i(TAG, "completed - $result")

                        if (result != null) {
                            // save token in data store
                            saveToken(result.code)

                            _state.update {
                                it.copy(

                                    id = result.user.id ?: "",

                                    firstName = result.user.firstName ?: "",
                                    secondName = result.user.secondName ?: "",
                                    thirdName = result.user.thirdName ?: "",

                                    gender = result.user.gender ?: "",
                                    dateOfBirth = result.user.dateOfBirth ?: "",

                                    profileImg = "https://storage.yandexcloud.net/myolimp/user/avatar/${result.user.id}.webp",

                                    email = result.user.email ?: "",
                                    snils = result.user.snils ?: "",

                                    grade = result.user.grade ?: 0,

                                    accountType = result.user.accountType ?: "",

//                                    region = result.user.region

                                )
                            }

                        }

                        // Error if user is empty
                    },
                    onError = {
                        Log.i(TAG, "error occurred - $it")
                    }
                )
            } catch (ex: Exception) {
                Log.i(TAG, "exception - ${ex.message}")
            }
        }
    }

    private fun saveUserData() {
        Log.i(TAG, "update object user -\n${_state.value}")
    }

    private suspend fun updateUserData(
        user: UserModel,
    ) {
        try {
            val response = repository.updateUser(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN)?: throw Exception("No access token"),
                user = user
            )

            Log.i(TAG, "response - ${response.body()}")
        } catch (ex: Exception) {
            Log.i(TAG, "ex - ${ex.message}")
        }
    }

    private suspend fun updateUserImg() {
        try {
            repository.updateUserImg(
                auth = dataStore.getToken(Constants.ACCESS_TOKEN)?: throw Exception("No access token"),
                imgArray = "" //TODO() Understand how to transfer image as string
            )
            Log.i(TAG, "response - success")
        } catch (ex: Exception) {
            Log.i(TAG, "ex - ${ex.message}")
        }
    }

    private fun saveToken(
        token: String
    ) {
        viewModelScope.launch {
            dataStore.setToken(
                key = Constants.ACCESS_TOKEN,
                value = token
            )
        }
    }

}
