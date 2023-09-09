package ramble.sokol.myolimp.feature_profile.utils

import androidx.navigation.NavController
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseCityModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseRegionModel
import ramble.sokol.myolimp.feature_authentication.data.models.ResponseSchoolModel

sealed class ProfileEvent {
    data object OnSave: ProfileEvent()
    data object OnImgSave: ProfileEvent()
    data object OnImgDelete: ProfileEvent()
    data object OnRefreshToken: ProfileEvent()
    data class OnFirstNameChanged(val firstName: String): ProfileEvent()
    data class OnSecondNameChanged(val secondName: String): ProfileEvent()
    data class OnThirdNameChanged(val thirdName: String): ProfileEvent()
    data class OnDobChanged(val dob: String): ProfileEvent()
    data class OnGenderChanged(val gender: String): ProfileEvent()
    data class OnSnilsChanged(val snils: String): ProfileEvent()
    data class OnImgChanged(val img: String?): ProfileEvent()
    data class OnMarkerClicked(val hasThird: Boolean): ProfileEvent()
    data class OnRegionChanged(val region: ResponseRegionModel): ProfileEvent()
    data class OnCityChanged(val city: ResponseCityModel): ProfileEvent()
    data class OnSchoolChanged(val school: ResponseSchoolModel): ProfileEvent()
    data class OnGradeChanged(val grade: Int): ProfileEvent()
    data class OnEmailChanged(val email: String): ProfileEvent()
    data class OnPhoneChanged(val phone: String): ProfileEvent()
    data class OnLogOut(val navigator: NavController): ProfileEvent()
}