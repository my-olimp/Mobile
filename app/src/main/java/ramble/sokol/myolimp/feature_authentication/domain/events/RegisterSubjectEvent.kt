package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed interface RegisterSubjectEvent {
    data class OnSearchQueryUpdated(val query: String): RegisterSubjectEvent
    data class OnSearched(val isSearching: Boolean): RegisterSubjectEvent
    data class OnSubjectClicked(val subject: String) : RegisterSubjectEvent
    data class OnNext(val navigator: DestinationsNavigator,val isWork: Boolean) : RegisterSubjectEvent
}
