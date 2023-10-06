package ramble.sokol.myolimp.feature_profile.domain.states

import android.net.Uri

data class ImgState (
    val isImgChanged: Boolean = false,
    val profileImg: Uri? = null
)
