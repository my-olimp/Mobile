package ramble.sokol.myolimp.feature_authentication.presentation.states

import android.net.Uri

data class RegisterImageState(
    val snils: String = "",
    val profileImg: Uri? = null
)