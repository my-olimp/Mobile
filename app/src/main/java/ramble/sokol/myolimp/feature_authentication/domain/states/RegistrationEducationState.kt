package ramble.sokol.myolimp.feature_authentication.domain.states

import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.utils.State

data class RegistrationEducationState(
    val region: Region = Region(),
    val city: City = City(),
    val school: School = School(),

    val grade: String = "",

    val isLoading: Boolean = false,

    var regionError: Boolean = false,
    var cityError: Boolean = false,
    var schoolError: Boolean = false,
    var gradeError: Boolean = false,

    val regionList: List<Region> = listOf(),
    val cityList: List<City> = listOf(),
    val schoolList: List<School> = listOf(),

    val isError: Boolean = false
) : State<RegistrationEducationState> {
    override fun onError(): RegistrationEducationState {
        return this.copy(isError = true)
    }

    override fun onLoaderUpdate(value: Boolean): RegistrationEducationState {
        return this.copy(isLoading = value)
    }

}
