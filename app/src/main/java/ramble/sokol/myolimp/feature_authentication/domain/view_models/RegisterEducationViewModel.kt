package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.destinations.RegisterSubjectsScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.asListCity
import ramble.sokol.myolimp.feature_authentication.data.models.asListRegion
import ramble.sokol.myolimp.feature_authentication.data.models.asListSchool
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEducationEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationEducationState
import ramble.sokol.myolimp.feature_profile.data.Constants

class RegisterEducationViewModel : ViewModel() {

    companion object {
        private const val TAG : String = "RegistrationEducationViewModel"
    }

    private val repository = RegistrationRepository()

    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(RegistrationEducationState())
    val state = _state.asStateFlow()

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
                _state.update {
                    it.copy(
                        region = event.region,
                        regionError = false,
                    )
                }
                requestCities(event.region.number)
                requestSchools(event.region.number)
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
                        onResult = {
                            event.navigator.navigate(RegisterSubjectsScreenDestination)
                            Log.i(TAG,"request called result")
                        },
                        onError = {
                            Log.i(TAG,"request called error")
                        }
                    )
                } else {
                    // TODO: Make SnackBar

//                    Toast.makeText(context,"data isn't valid",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun sendRequest(
        onResult: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val userData = state.value
            try {
                repository.registerEducation(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN)?: throw Exception("No access token"),
                    data = UserEducationDataModel(
                        regionId = userData.region.number,
                        cityId = userData.city.id,
                        schoolId = userData.school.id,
                        grade = userData.grade.toInt()
                    ),
                    onResult = {
                        Log.i(TAG,"patch request response: $it")
                        onResult.invoke()
                    },
                    onError = {
                        onError.invoke()
                        Log.i(TAG,"patch request exception: ${it.message}")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG,"exception: ${e.message}")
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

    fun loadRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getRegions(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN) ?: throw Exception("no access token"),
                    onResult = { response ->
                        Log.i(TAG,"response regions $response")
                        if(response != null) {
                            _state.update {
                                it.copy(
                                    regionList = response.asListRegion()
                                )
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG,"throwed ${it.message}")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG,"exception: ${e.message}")
            }
        }
    }

    private fun requestCities(regionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.i(TAG,"try to request with $regionId region id")
                repository.getCities(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN) ?: throw Exception("no access token"),
                    data = regionId,
                    onResult = { list ->
                        Log.i(TAG,"cities request response is $list")
                        if(list != null) {
                            _state.update {
                                it.copy(
                                    cityList = list.asListCity()
                                )
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG,"cities request cause $it")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG,"cities exception: ${e.message}")
            }
        }
    }
    private fun requestSchools(regionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getSchools(
                    auth = dataStore.getToken(Constants.ACCESS_TOKEN) ?: throw Exception("No access token"),
                    data = regionId,
                    onResult = { list ->
                        Log.i(TAG,"response of school request: $list")
                        if(list != null) {
                            _state.update {
                                it.copy(
                                    schoolList = list.asListSchool()
                                )
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG,"school response is exception: ${it.message}")
                    }
                )
            } catch(e: Exception) {
                Log.i(TAG,"school throwed exception ${e.message}")
            }
        }
    }
}
