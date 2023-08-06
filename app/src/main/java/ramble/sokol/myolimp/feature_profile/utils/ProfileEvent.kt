package ramble.sokol.myolimp.feature_profile.utils

import android.graphics.Bitmap

sealed class ProfileEvent {
    data class OnFirstNameChanged(val firstName: String): ProfileEvent()
    data class OnSecondNameChanged(val secondName: String): ProfileEvent()
    data class OnThirdNameChanged(val thirdName: String): ProfileEvent()
    data class OnDobChanged(val dob: String): ProfileEvent()
    data class OnGenderChanged(val gender: String): ProfileEvent()
    data class OnSnilsChanged(val snils: String): ProfileEvent()
    data class OnImgChanged(val img: Bitmap): ProfileEvent()
    data class OnMarkerClicked(val hasThird: Boolean): ProfileEvent()
    data object OnSave: ProfileEvent()
}