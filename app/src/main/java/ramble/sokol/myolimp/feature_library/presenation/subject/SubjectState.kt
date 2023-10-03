package ramble.sokol.myolimp.feature_library.presenation.subject

import ramble.sokol.myolimp.feature_authentication.data.models.SubjectModel
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class SubjectState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchQuery: String = "",
    val isShowingFavourites: Boolean = false,
    val subjectName: String = ""
)
