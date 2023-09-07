package ramble.sokol.myolimp.feature_authentication.domain.events

import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel

sealed interface RegisterSubjectEvent {
    data class OnSubjectClicked(val subject: RequestSubjectModel) : RegisterSubjectEvent
}
