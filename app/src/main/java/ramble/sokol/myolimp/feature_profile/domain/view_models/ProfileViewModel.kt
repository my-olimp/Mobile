package ramble.sokol.myolimp.feature_profile.domain.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ramble.sokol.myolimp.feature_profile.domain.models.UserModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG : String = "VMProfile"
    }

    private val _state = mutableStateOf (
        UserModel(
            firstName = "Диана",
            secondName = "Спиридонова",
            thirdName = "Романовна",
            dateOfBirth = "25.05.2007",
            gender = "Женский",
            snils = "123-456-789-99",
            profileImg = null,
            hasThird = false
            )
        )

    val state: State<UserModel> = _state

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

            is ProfileEvent.OnSave -> {
                saveUserData()
            }
        }
    }

    private fun saveUserData() {
        Log.i(TAG, "update object user -\n${_state.value}")
    }

}
