package ramble.sokol.myolimp.feature_profile.domain.view_models

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG : String = "ViewModelProfile"
    }


    private val _state = mutableStateOf (
        UserModel (
            firstName = "Диана",
            secondName = "Спиридонова",
            thirdName = "Романовна",
            dateOfBirth = "25.05.2007",
            gender = "Женский",
            snils = "123-456-789-99",
            region = "Московская область",
            city = "Чехов",
            school = "МБОУ СОШ №10",
            email = "aleshka@mail.ru",
            phone = "+7 123 456 78 90",
            grade = 10,
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

            is ProfileEvent.OnImgDelete -> {
                _state.value = _state.value.copy(
                    profileImg = null
                )
            }

            is ProfileEvent.OnSave -> {
                saveUserData()
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
        }
    }

    private fun saveUserData() {
        Log.i(TAG, "update object user -\n${_state.value}")
    }

}
