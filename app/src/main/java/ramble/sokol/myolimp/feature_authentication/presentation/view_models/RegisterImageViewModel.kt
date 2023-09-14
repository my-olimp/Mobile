package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterImageState
import ramble.sokol.myolimp.feature_profile.data.Constants
import java.io.File
import java.io.FileOutputStream

class RegisterImageViewModel : ViewModel() {

    private val repository = RegistrationRepository()
    private val dataStore = CodeDataStore()

    private val _state = MutableStateFlow(RegisterImageState())
    val state = _state.asStateFlow()

    fun onEvent(
        event: RegistrationImageEvent
    ) {
        when (event) {
            is RegistrationImageEvent.OnSnilsChanged -> {
                _state.update { value ->
                    value.copy(
                        snils = event.snils
                    )
                }
            }

            is RegistrationImageEvent.OnImageChanged -> {
                _state.update { value ->
                    value.copy(
                        profileImg = event.uri
                    )
                }
            }

            is RegistrationImageEvent.OnSubmit -> {
                viewModelScope.launch {
                    try {
                        // Write the bitmap to the new File in PNG format
                        val outStream = FileOutputStream(event.file)
                        event.bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream)
                        outStream.flush()
                        outStream.close()

                        //Prepate image for upload
                        val requestFile = event.file.asRequestBody("image/png".toMediaTypeOrNull())
                        val body =
                            MultipartBody.Part.createFormData("image", event.file.name, requestFile)

                        //Upload image
                        repository.registerImageDocs(
                            auth = dataStore.getToken(Constants.ACCESS_TOKEN)
                                ?: throw Exception("No access token"),
                            data = UserDocsDataModel(
                                snils = state.value.snils
                            ),
                            imageBody = body,
                            onResult = {
                                Log.i("RegistrerImageViewModel", "success,\n $it") // TODO()
                                event.navigator.navigate(
                                    HomeScreenDestination()
                                ) {
                                    popUpTo(NavGraphs.root) {
                                        saveState = false
                                    }
                                    launchSingleTop = false
                                    restoreState = false
                                }
                            },
                            onError = {
                                throw it
                            }
                        )
                    } catch (e: Exception) {
                        Log.i("RegisterImageViewModel", "exception: ${e.message}")
                    }
                }
            }

            is RegistrationImageEvent.OnUploadError -> {} // TODO add error handing
        }
    }
}

sealed interface RegistrationImageEvent {
    data class OnSnilsChanged(val snils: String) : RegistrationImageEvent
    data class OnImageChanged(val uri: Uri?) : RegistrationImageEvent
    data class OnSubmit(val file: File, val bitmap: Bitmap, val navigator: DestinationsNavigator) : RegistrationImageEvent
    data object OnUploadError : RegistrationImageEvent
}
