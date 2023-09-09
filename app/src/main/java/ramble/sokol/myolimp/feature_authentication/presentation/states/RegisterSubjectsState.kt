package ramble.sokol.myolimp.feature_authentication.presentation.states

import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel
import ramble.sokol.myolimp.feature_authentication.data.models.SubjectModel

data class RegisterSubjectsState (
    val searchQuery : String = "",
    val isSearching : Boolean = false,

    var isNextEnabled : Boolean = false,

    val subjects : MutableList<SubjectModel> = mutableListOf(),
    val chosenSubjects : MutableList<RequestSubjectModel> = mutableListOf()
)