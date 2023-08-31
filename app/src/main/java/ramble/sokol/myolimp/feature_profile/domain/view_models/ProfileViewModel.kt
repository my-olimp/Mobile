package ramble.sokol.myolimp.feature_profile.domain.view_models

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.BeginAuthenticationScreenDestination
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.states.SignUpState
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent

class ProfileViewModel (
    context: Context
) : ViewModel() {

    companion object {
        private const val TAG : String = "ViewModelProfile"
    }

    private val dataStore = CodeDataStore(context = context)

    private val repository = ProfileRepository()

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

                    event.navigator.navigate(BeginAuthenticationScreenDestination)
                }
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

}
