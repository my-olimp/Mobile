package ramble.sokol.myolimp.feature_authentication.domain.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.feature_authentication.data.models.UserEducationDataModel
import ramble.sokol.myolimp.feature_authentication.data.models.asListRegion
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationEducationEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.states.RegistrationEducationState
import ramble.sokol.myolimp.feature_profile.data.Constants

class RegisterEducationViewModel(
    val context: Context
) : ViewModel() {

    companion object {
        private const val TAG : String = "RegistrationEducationViewModel"
    }


    private val repository = RegistrationRepository(context)

    private val dataStore = CodeDataStore(context)

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
                        regionError = false
                    )
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
                        onResult = {
                            //event.navigator.navigate("" /*TODO navigate*/)
                            Log.i(TAG,"request called result")
                        },
                        onError = {
                            Log.i(TAG,"request called error")
                        }
                    )
                } else {
                    Toast.makeText(context,"data isnt valid",Toast.LENGTH_LONG).show()
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
                       region = userData.region.asResponseModel(),
                       city = userData.city,
                       school = userData.school,
                       grade = userData.grade.toInt()
                   ),
                   onResult = {
                       Log.i(TAG,"request response: $it")
                       onResult.invoke()
                   },
                   onError = {
                       onError.invoke()
                       Log.i(TAG,"request exception: ${it.message}")
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
        if (state.value.city == "") {
            _state.update { it.copy(cityError = true) }
            isValid = false
        }
        if (state.value.school == "") {
            _state.update { it.copy(schoolError = true) }
            isValid = false
        }
        if (state.value.grade == "") {
            _state.update { it.copy(gradeError = true) }
            isValid = false
        }
        return isValid
    }

    fun loadData() {
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
}
