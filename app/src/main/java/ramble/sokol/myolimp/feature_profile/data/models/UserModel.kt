package ramble.sokol.myolimp.feature_profile.data.models

import android.net.Uri

data class UserModel (
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val dateOfBirth: String,
    val gender: String,
    val snils: String,
    val region: String,
    val city: String,
    val school: String,
    val phone: String,
    val email: String,
    val grade: Int,
    val profileImg: Uri?,
    val hasThird: Boolean,
)