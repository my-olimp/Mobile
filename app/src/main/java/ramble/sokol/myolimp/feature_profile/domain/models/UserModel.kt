package ramble.sokol.myolimp.feature_profile.domain.models

import android.graphics.Bitmap

data class UserModel(
    val firstName: String,
    val secondName: String,
    val thirdName: String,
    val dateOfBirth: String,
    val gender: String,
    val snils: String,
    val profileImg: Bitmap?,
    val hasThird: Boolean,
)