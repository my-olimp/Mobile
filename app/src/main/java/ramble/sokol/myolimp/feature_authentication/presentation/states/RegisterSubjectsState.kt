package ramble.sokol.myolimp.feature_authentication.presentation.states

data class RegisterSubjectsState (
    val searchQuery : String = "",
    val isSearching : Boolean = false,

    var isNextEnabled : Boolean = false,
    var isLoading : Boolean = false,

    val subjects : List<String> = mutableListOf(),
    val chosenSubjects : MutableList<String> = mutableListOf()
)