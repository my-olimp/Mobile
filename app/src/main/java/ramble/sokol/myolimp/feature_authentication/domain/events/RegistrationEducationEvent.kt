package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

interface RegistrationEducationEvent {

    data class OnNext(val navigator: DestinationsNavigator) : RegistrationEducationEvent

    data class OnSchoolChanged(val school: School) : RegistrationEducationEvent

    data class OnRegionChanged(val region: Region) : RegistrationEducationEvent

    data class OnCityChanged(val city: String) : RegistrationEducationEvent

    data class OnGradeChanged(val grade: String) : RegistrationEducationEvent
}
