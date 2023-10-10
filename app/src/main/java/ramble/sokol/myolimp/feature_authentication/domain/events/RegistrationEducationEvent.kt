package ramble.sokol.myolimp.feature_authentication.domain.events

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School

sealed interface RegistrationEducationEvent {

    data object OnCancelLoader : RegistrationEducationEvent

    data object OnStartLoader: RegistrationEducationEvent

    data class OnNext(val navigator: DestinationsNavigator, val isWork: Boolean) : RegistrationEducationEvent

    data class OnSchoolChanged(val school: School) : RegistrationEducationEvent

    data class OnRegionChanged(val region: Region) : RegistrationEducationEvent

    data class OnCityChanged(val city: City) : RegistrationEducationEvent

    data class OnGradeChanged(val grade: String) : RegistrationEducationEvent
}
