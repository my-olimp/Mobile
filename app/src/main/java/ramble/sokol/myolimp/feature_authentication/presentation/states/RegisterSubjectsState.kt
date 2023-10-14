package ramble.sokol.myolimp.feature_authentication.presentation.states

import ramble.sokol.myolimp.utils.State

data class RegisterSubjectsState (
    val searchQuery : String = "",
    val isSearching : Boolean = false,

    var isNextEnabled : Boolean = false,
    var isLoading : Boolean = false,
    val isError: Boolean = false,

    val subjects : List<String> = mutableListOf(),
    val chosenSubjects : MutableList<String> = mutableListOf()
) : State<RegisterSubjectsState> {
    override fun onError(): RegisterSubjectsState {
        return this.copy(isError = true)
    }

    override fun onLoaderUpdate(value: Boolean): RegisterSubjectsState {
        return this.copy(isLoading = value)
    }

}