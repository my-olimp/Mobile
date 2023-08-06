package ramble.sokol.myolimp.feature_profile.domain.models

import android.net.Uri

data class UserModel (
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val dateOfBirth: String,
    val gender: String,
    val snils: String,
    val profileImg: Uri?,
    val hasThird: Boolean,
)