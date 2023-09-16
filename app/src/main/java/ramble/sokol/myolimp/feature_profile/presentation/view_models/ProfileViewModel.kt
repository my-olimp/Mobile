package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ramble.sokol.myolimp.destinations.BeginAuthenticationScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.asListCity
import ramble.sokol.myolimp.feature_authentication.data.models.asListRegion
import ramble.sokol.myolimp.feature_authentication.data.models.asListSchool
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.COOKIES
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileState
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.view_models.LocalUserModel
import ramble.sokol.myolimp.utils.exceptions.NetworkConnectivityException
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel (
    context: Context
) : ViewModel() {

    companion object {
        private const val TAG: String = "ViewModelProfile"
    }

    private val dataStore = CodeDataStore()

    private val repository = ProfileRepository()

    private val userDatabase : UserDatabase = UserDatabase.invoke(context)
    private var userRepository : LocalUserRepository = LocalUserRepository(database = userDatabase)

    private val _state = MutableStateFlow(
        ProfileState()
    )

    private val _user = userRepository.getUser()

    val state = combine(_state, _user) { state, user ->
        state.copy(
            user = user,
            firstName = user.firstName,
            secondName = user.secondName,
            thirdName = user.thirdName,
            dateOfBirth = user.dateOfBirth,
            snils = user.snils,
            gender = user.gender,
            region = user.region,
            city = user.city,
            school = user.school,
            phone = user.phone,
            email = user.email,
            grade = user.grade,
            profileImg = user.profileImg,
            hasThird = user.hasThird,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        ProfileState()
    )

//    private val _educationState = MutableStateFlow(
//        ProfileEducationState()
//    )
//    private val educationState = _educationState.asStateFlow()

    fun onEvent (
        event: ProfileEvent
    ) {
        when (event) {
            is ProfileEvent.OnFirstNameChanged -> {
                _state.update {
                    it.copy(
                        firstName = event.firstName

                    )
                }
            }

            is ProfileEvent.OnSecondNameChanged -> {
                _state.value = _state.value.copy(
                    secondName = event.secondName
                )
            }

            is ProfileEvent.OnThirdNameChanged -> {
                _state.value = _state.value.copy(
                    thirdName = event.thirdName
                )
            }

            is ProfileEvent.OnDobChanged -> {
                _state.value = _state.value.copy(
                    dateOfBirth = event.dob
                )
            }

            is ProfileEvent.OnGenderChanged -> {
                _state.value = _state.value.copy(
                    gender = event.gender
                )
            }

            is ProfileEvent.OnSnilsChanged -> {
                _state.value = _state.value.copy(
                    snils = event.snils
                )
            }

            is ProfileEvent.OnImgChanged -> {
                _state.value = _state.value.copy(
                    profileImg = event.uri.toString()
                )
            }

            is ProfileEvent.OnMarkerClicked -> {
                _state.value = _state.value.copy(
                    hasThird = event.hasThird
                )
            }

            is ProfileEvent.OnImgDelete -> {
                _state.value = _state.value.copy(
                    profileImg = null
                )
            }

            is ProfileEvent.OnSave -> {
                if (isValidData()) {
                    viewModelScope.launch {
                        updateUserData()
                    }
                    SheetRouter.navigateTo(SheetNavigation.Empty())
                } else {
                    Log.i(TAG, "data is not valid")
                }
            }

            is ProfileEvent.OnPersonalInfoSave -> {
                viewModelScope.launch {
                    updateUserData()
                }
                SheetRouter.navigateTo(SheetNavigation.Empty())
            }

            is ProfileEvent.OnImgSave -> {
                viewModelScope.launch {
                    updateUserImg(
                        file = event.file,
                        bitmap = event.bitmap,
                        onResult = {
                            Log.i("TEMP", "hide sheet")
                            SheetRouter.navigateTo(SheetNavigation.Empty()) }
                    )
                }
            }

            is ProfileEvent.OnRegionChanged -> {
                _state.value = _state.value.copy(
                    region = event.region,
                    regionError = false
                )
                viewModelScope.launch {
                    updateCitiesList()
                    updateSchoolsList()
                }
            }

            is ProfileEvent.OnCityChanged -> {
                _state.value = _state.value.copy(
                    city = event.city,
                    cityError = false
                )
            }

            is ProfileEvent.OnSchoolChanged -> {
                _state.value = _state.value.copy(
                    school = event.school,
                    schoolError = false
                )
            }

            is ProfileEvent.OnGradeChanged -> {
                _state.value = _state.value.copy(
                    grade = event.grade,
                    gradeError = false
                )
            }

            is ProfileEvent.OnPhoneChanged -> {
                _state.value = _state.value.copy(
                    phone = event.phone
                )
            }

            is ProfileEvent.OnEmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }

            is ProfileEvent.OnLogOut -> {
                viewModelScope.launch {
                    dataStore.deleteToken()

                    event.navigator.popBackStack()
                    event.navigator.navigate(BeginAuthenticationScreenDestination)

                    try {
                        repository.logOut(
                            cookie = dataStore.getToken(COOKIES).first() ?: throw Exception("no refresh"),
                            onResult = {
                                Log.i(TAG, "completed")
                            },
                            onError = {
                                Log.i(TAG, "error occurred - $it")
                            }
                        )
                    } catch (ex: Exception) {
                        Log.i(TAG, "exception - ${ex.message}")
                    }
                }
            }

            is ProfileEvent.OnRefreshToken -> {
//                refreshToken()
            }

            ProfileEvent.OnUploadError -> TODO()
            
            is ProfileEvent.OnAttachSheet -> {
                updateMenus()
//                with(state.value) {
//                    _educationState.update {
//                        it.copy(
//                            region = this.region,
//                            city = this.city,
//                            school = this.school,
//                            grade = this.grade
//                        )
//                    }
//                }
            }

            is ProfileEvent.OnCancelSheet -> {
//                with(educationState.value) {
//                    _state.update {
//                        it.copy(
//                            region = this.region,
//                            city = this.city,
//                            school = this.school,
//                            grade = this.grade
//                        )
//                    }
//                }
            }
        }
    }



    private fun refreshToken() {
        viewModelScope.launch {
            try {

                repository.refreshToken (
                    cookie = dataStore.getToken(COOKIES).first()
                        ?: throw Exception("no cookie token"),
                    onResult = { result ->

                        if (result != null) {
                            Log.i(TAG, "completed - $result")

                            // save token in data store
                            saveToken(result.code)

                            _state.update {
                                it.copy(

                                    id = result.user.id,

                                    firstName = result.user.firstName ?: "",
                                    secondName = result.user.secondName ?: "",
                                    thirdName = result.user.thirdName ?: "",

                                    gender = result.user.gender ?: "",
                                    dateOfBirth = result.user.dateOfBirth ?: "",

                                    profileImg = "https://storage.yandexcloud.net/myolimp/user/avatar/${result.user.id}.webp",

                                    email = result.user.email ?: "",
                                    phone = result.user.phone ?: "",
                                    snils = result.user.snils ?: "",

                                    grade = result.user.grade ?: 0,
                                    accountType = result.user.accountType ?: "",

//                                    region = (result.user.region
//                                        ?: ResponseRegionModel()).asRegion(),
//                                    city = (result.user.city ?: ResponseCityModel()).asCity(),
//                                    school = (result.user.school
//                                        ?: ResponseSchoolModel()).asSchool(),

                                    )
                            }

                            Log.i(TAG, "user - ${userRepository.getUser()}")

                        } else {
                            Log.i(TAG, "can not get user")
                        }
                    },
                    onError = {

                        if (it is NetworkConnectivityException) {
                            Log.i(TAG, "there is no network - $it")
                        }

                        Log.i(TAG, "error occurred - $it")
                    }
                )
            } catch (ex: Exception) {
                Log.i(TAG, "exception - ${ex.message}")
            }
        }
    }

    private suspend fun updateUserData() {
        try {
            val user = LocalUserModel(
                id = "12",
                firstName = _state.value.firstName!!,
                secondName = _state.value.firstName!!,
                thirdName = _state.value.firstName!!,
                email = "awd@mail.ru",
            )

            Log.i(TAG, "useeeer - $user")
            val response = repository.updateUser(
                auth = dataStore.getToken(ACCESS_TOKEN).first()
                    ?: throw Exception("No access token"),
                user = user
            )

            Log.i(TAG, "response - ${response.body()}")
        } catch (ex: Exception) {
            Log.i(TAG, "ex - ${ex.message}")
        }
    }

    private suspend fun updateUserImg(
        file: File,
        bitmap: Bitmap,
        onResult: () -> Unit,
    ) {
        try {
            // Write the bitmap to the new File in PNG format
            val outStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream)
            outStream.flush()
            outStream.close()

            //Prepate image for upload
            val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
            val body =
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            repository.uploadImg(
                auth = dataStore.getToken(ACCESS_TOKEN).first()
                    ?: throw Exception("No access token"),
                imageBody = body,
                onResult = onResult,
                onError = { throw it }
            )
            Log.i(TAG, "response - success")
        } catch (ex: Exception) {
            Log.i(TAG, "ex - ${ex.message}")
        }
    }

    private fun saveToken(
        token: String
    ) {
        viewModelScope.launch {
            dataStore.setToken(
                key = ACCESS_TOKEN,
                value = token
            )
        }
    }

    private fun updateRegionsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getRegions(
                    auth = dataStore.getToken(ACCESS_TOKEN).first()
                        ?: throw Exception("no access token"),
                    onResult = { list ->
                        Log.i(TAG, "response region list: $list")
                        if (list != null) {
                            _state.update {
                                it.copy(regionList = list.asListRegion())
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG, "region exception $it")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG, "region request throw ${e.message}")
            }
        }
    }

    private fun updateCitiesList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getCities(
                    auth = dataStore.getToken(ACCESS_TOKEN).first()
                        ?: throw Exception("no access token"),
                    regionId = state.value.region?.number
                        ?: throw Exception("null region"),
                    onResult = { list ->
                        Log.i(TAG, "response city list: $list")
                        if (list != null) {
                            _state.update {
                                it.copy(cityList = list.asListCity())
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG, "city exception $it")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG, "city request throw ${e.message}")
            }
        }
    }

    private fun updateSchoolsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getSchools(
                    auth = dataStore.getToken(ACCESS_TOKEN).first()
                        ?: throw Exception("no access token"),
                    regionId = state.value.region?.number
                        ?: throw Exception("null region"),
                    onResult = { list ->
                        Log.i(TAG, "response school list: $list")
                        if (list != null) {
                            _state.update {
                                it.copy(schoolList = list.asListSchool())
                            }
                        }
                    },
                    onError = {
                        Log.i(TAG, "school exception $it")
                    }
                )
            } catch (e: Exception) {
                Log.i(TAG, "school request throw ${e.message}")
            }
        }
    }

    private fun isValidData(): Boolean {

        var isValid = true

        with(state.value) {
            if (this.regionList.find { it == this.region } == null) {
                isValid = false
                _state.update { it.copy(regionError = true) }
            }
            if (this.cityList.find { it == this.city } == null) {
                isValid = false
                _state.update { it.copy(cityError = true) }
            }
            if (this.schoolList.find { it == this.school } == null) {
                isValid = false
                _state.update { it.copy(schoolError = true) }
            }
            if (this.grade == 0) {
                isValid = false
                _state.update { it.copy(gradeError = true) }
            }
            if (snils != null && snils.length != 11 || snils != null && !snils.isDigitsOnly()) {
                isValid = false
                _state.update { it.copy(snilsError = true) }
            }
        }

        return isValid
    }

    private fun updateMenus() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshErrors()
            updateRegionsList()
            if (state.value.region?.name != "") {
                updateCitiesList()
                updateSchoolsList()
            }
        }
    }

    private fun refreshErrors() {
        _state.update {
            it.copy(
                regionError = false,
                cityError = false,
                schoolError = false,
                gradeError = false
            )
        }
    }
}

