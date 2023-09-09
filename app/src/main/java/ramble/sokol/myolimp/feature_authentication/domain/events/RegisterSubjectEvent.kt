package ramble.sokol.myolimp.feature_authentication.domain.events

import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel

sealed interface RegisterSubjectEvent {
    data object OnLoadSubjects: RegisterSubjectEvent
    data class OnSearchQueryUpdated(val query: String): RegisterSubjectEvent
    data class OnSearched(val isSearching: Boolean): RegisterSubjectEvent
    data class OnSubjectClicked(val subject: RequestSubjectModel) : RegisterSubjectEvent
}
