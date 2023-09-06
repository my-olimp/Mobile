package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.domain.view_models.RegisterInfoViewModel
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterImageState
import ramble.sokol.myolimp.feature_profile.data.Constants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RegisterImageViewModel(
    val context: Context
) : ViewModel() {

    private val repository = RegistrationRepository(context = context)
    private val dataStore = CodeDataStore(context = context)

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

            is RegistrationImageEvent.OnUploadError -> {} // add error handing
        }
    }

    fun onNext(imageFile: File, bitmap: Bitmap) {
        viewModelScope.launch {
            try {
                // Write the bitmap to the new File in PNG format
                val outStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream)
                outStream.flush()
                outStream.close()

                //Prepate image for upload
                val requestFile = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
                val body =
                    MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

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
}

sealed interface RegistrationImageEvent {
    data class OnSnilsChanged(val snils: String) : RegistrationImageEvent
    data class OnImageChanged(val uri: Uri?) : RegistrationImageEvent
    data object OnUploadError : RegistrationImageEvent
}
