package ramble.sokol.myolimp.feature_profile.utils

import android.net.Uri
import androidx.navigation.NavController
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

sealed class ProfileEvent {

    /*TODO REMOVE AFTER TESTING*/
    data class OnChangeType(val newType: String) : ProfileEvent()
    /**/

    data object OnCancelEducationLoader: ProfileEvent()

    data object OnStartEducatutionLoader: ProfileEvent()

    data object OnEducationInfoSave: ProfileEvent()
    data object OnPersonalInfoSave: ProfileEvent()
    data object OnContactsInfoSave : ProfileEvent()
    data object OnImgSave : ProfileEvent()
    data object OnImgUpdated : ProfileEvent()
    data object OnDeleteImg : ProfileEvent()

    data class OnEducationSheetAttach(val region: Region) : ProfileEvent()

    data class OnImgChanged(val uri: Uri?): ProfileEvent()

    data class OnFirstNameChanged(val firstName: String): ProfileEvent()
    data class OnSecondNameChanged(val secondName: String): ProfileEvent()
    data class OnThirdNameChanged(val thirdName: String): ProfileEvent()
    data class OnDobChanged(val dob: String): ProfileEvent()
    data class OnGenderChanged(val gender: String): ProfileEvent()
    data class OnSnilsChanged(val snils: String): ProfileEvent()
    data class OnMarkerClicked(val hasThird: Boolean): ProfileEvent()

    data class OnRegionChanged(val region: Region): ProfileEvent()
    data class OnCityChanged(val city: City): ProfileEvent()
    data class OnSchoolChanged(val school: School): ProfileEvent()
    data class OnGradeChanged(val grade: Int): ProfileEvent()

    data class OnEmailChanged(val email: String): ProfileEvent()
    data class OnPhoneChanged(val phone: String): ProfileEvent()

    data class OnLogOut(val navigator: NavController): ProfileEvent()
}
