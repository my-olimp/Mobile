package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.update
import ramble.sokol.myolimp.destinations.RegisterSubjectsScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.asListCity
import ramble.sokol.myolimp.feature_authentication.data.models.asListRegion
import ramble.sokol.myolimp.feature_authentication.data.models.asListSchool
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEducationEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationEducationState
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions

class RegisterEducationViewModel : BaseViewModel<RegistrationEducationState>(
    RegistrationEducationState()
) {

    companion object {
        private const val TAG : String = "RegistrationEducationViewModel"
    }

    private val repository = RegistrationRepository()

    init {
        this.updateLoader(true)
        loadRegions()
    }

    fun onEvent(
        event: RegistrationEducationEvent
    ) {
        when(event) {
            is RegistrationEducationEvent.OnGradeChanged -> {
                _state.update {
                    it.copy(
                        grade = event.grade,
                        gradeError = false
                    )
                }
            }
            is RegistrationEducationEvent.OnCityChanged -> {
                _state.update {
                    it.copy(
                        city = event.city,
                        cityError = false
                    )
                }
            }
            is RegistrationEducationEvent.OnRegionChanged -> {
                if(event.region != Region()) {
                    _state.update {
                        it.copy(
                            region = event.region,
                            regionError = false,
                            isLoading = true,
                            cityList = emptyList(),
                            schoolList = emptyList()
                        )
                    }
                    launchIO {
                        requestCities(event.region.number)
                        requestSchools(event.region.number)
                    }
                }
            }
            is RegistrationEducationEvent.OnSchoolChanged -> {
                _state.update {
                    it.copy(
                        school = event.school,
                        schoolError = false
                    )
                }
            }
            is RegistrationEducationEvent.OnNext -> {
                if(checkData()) {
                    sendRequest(
                        navigator = event.navigator,
                        isWork = event.isWork
                    )
                }
            }
        }
    }

    private fun sendRequest(
        navigator: DestinationsNavigator,
        isWork: Boolean
    ) {
        updateLoader(true)
        launchIO {
            with(state.value) {
                repository.registerEducation(
                    data = UserEducationDataModel(
                        regionId = region.number,
                        cityId = city.id,
                        schoolId = school.id,
                        grade = if(!isWork)grade.toInt() else 1 /*TODO make work spot*/
                    ),
                    onResult = {
                        updateLoader(false)
                        Log.i(TAG, "patch request response: $it")
                        if(it != null) {
                            navigator.navigate(RegisterSubjectsScreenDestination(isWorkScreen = isWork))
                        } else castError()
                    },
                    onError = {
                        Log.i(TAG, "patch request exception: ${it.message}")
                        castError(ViewModelExceptions.Network)
                    }
                )
            }
        }
    }

    private fun checkData(): Boolean {
        var isValid = true
        if (state.value.region.name == "") {
            _state.update { it.copy(regionError = true) }
            isValid = false
        }
        if (state.value.city.name == "") {
            _state.update { it.copy(cityError = true) }
            isValid = false
        }
        if (state.value.school.name == "") {
            _state.update { it.copy(schoolError = true) }
            isValid = false
        }
        if (state.value.grade == "") {
            _state.update { it.copy(gradeError = true) }
            isValid = false
        }
        return isValid
    }

    private fun loadRegions() {
        launchIO {
            repository.getRegions(
                onResult = { response ->
                    Log.i(TAG, "response regions $response")
                    if (response != null) {
                        _state.update {
                            it.copy(
                                regionList = response.asListRegion(),
                            )

                        }
                    }
                    updateLoader(false)
                },
                onError = {
                    Log.i(TAG, "throwed ${it.message}")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

    private fun requestCities(regionId: Int) {
        launchIO {
            repository.getCities(
                data = regionId,
                onResult = { list ->
                    Log.i(TAG,"cities request response is $list")

                    if(list != null) {
                        _state.update {
                            it.copy(
                                cityList = list.asListCity()
                            )
                        }
                        if(state.value.schoolList.isNotEmpty())updateLoader(false)
                    }
                },
                onError = {
                    Log.i(TAG,"cities request cause $it")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

    private fun requestSchools(regionId: Int) {
        launchIO {
            repository.getSchools(
                data = regionId,
                onResult = { list ->
                    Log.i(TAG, "response of school request: $list")
                    if (list != null) {
                        _state.update {
                            it.copy(
                                schoolList = list.asListSchool()
                            )
                        }
                        if(state.value.cityList.isNotEmpty())updateLoader(false)
                    }
                },
                onError = {
                    Log.i(TAG, "school response is exception: ${it.message}")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }
}
