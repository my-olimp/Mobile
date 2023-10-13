package ramble.sokol.myolimp.feature_authentication.presentation.view_models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.destinations.HomeScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.UserDocsDataModel
import ramble.sokol.myolimp.feature_authentication.domain.events.RegistrationImageEvent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.RegistrationRepository
import ramble.sokol.myolimp.feature_authentication.presentation.states.RegisterImageState
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import java.io.File
import java.io.FileOutputStream

class RegisterImageViewModel : ViewModel(), KoinComponent {

    companion object {
        private const val TAG: String = "ViewModelImage"
    }

    private val repository = RegistrationRepository()
    private val profileRepository = ProfileRepository()

    private val context by inject<Context>()

    private val _state = MutableStateFlow(RegisterImageState())
    val state = _state.asStateFlow()

    private val userDatabase : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = userDatabase)

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

                updateLoading(true)

                viewModelScope.launch {

                    // upload img
                    uploadImg(
                        onResult = {
                            if (!state.value.isWorkScreen) {
                                // upload snils
                                repository.updateSnils(
                                    UserDocsDataModel(
                                        snils = state.value.snils,
                                    ),
                                    onError = {
                                        updateLoading(false)
                                    },
                                    onResult = { result->

                                        Log.i(TAG, "result - $result")

                                        if (result != null) {
                                            viewModelScope.launch {
                                                updateDatabase(result)
                                            }
                                        }

                                        event.navigator.navigate(
                                            HomeScreenDestination()
                                        ) {
                                            popUpTo(NavGraphs.root) {
                                                saveState = false
                                            }
                                            launchSingleTop = false
                                            restoreState = false
                                        }
                                    }
                                )
                            } else {
                                event.navigator.navigate(
                                    HomeScreenDestination()
                                ) {
                                    popUpTo(NavGraphs.root) {
                                        saveState = false
                                    }
                                    launchSingleTop = false
                                    restoreState = false
                                }
                            }
                        }
                    )
                }
            }

            is RegistrationImageEvent.OnChangedScreenType -> {
                _state.update {
                    it.copy(
                        isWorkScreen = event.isWorkScreen
                    )
                }
            }
        }
    }

    private suspend fun uploadImg(
        onResult: () -> Unit
    ) {
        try {

            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(
                state.value.profileImg ?: Uri.EMPTY)
            )

            val pngFile = File(context.cacheDir, "converted_image.png")

            if (pngFile.exists()) pngFile.delete()

            withContext(Dispatchers.IO) {
                pngFile.createNewFile()

                val outStream = FileOutputStream(pngFile)

                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream)

                outStream.flush()
                outStream.close()
            }

            // Preparation image for upload
            val requestFile = pngFile.asRequestBody("image/png".toMediaTypeOrNull())
            val body =
                MultipartBody.Part.createFormData("image", pngFile.name, requestFile)

            profileRepository.uploadImg(
                imageBody = body,
                onResult = onResult,
                onError = {
                    throw it
                }
            )

            Log.i(TAG, "response - success")

        } catch (ex: Exception) {
            Log.i(TAG, "error - $ex")
        }
    }

    private fun updateLoading(isLoading: Boolean) = _state.update {
        it.copy(
            isLoading = isLoading
        )
    }

    private suspend fun updateDatabase(response: ResponseUserModel) {
        userRepository.updateUser(
            user = response.toLocalUserModel()
        )
    }

}
