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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ramble.sokol.myolimp.destinations.BeginAuthenticationScreenDestination
import ramble.sokol.myolimp.feature_authentication.data.models.City
import ramble.sokol.myolimp.feature_authentication.data.models.Region
import ramble.sokol.myolimp.feature_authentication.data.models.School
import ramble.sokol.myolimp.feature_authentication.data.models.asListCity
import ramble.sokol.myolimp.feature_authentication.data.models.asListRegion
import ramble.sokol.myolimp.feature_authentication.data.models.asListSchool
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.ACCESS_TOKEN
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.COOKIES
import ramble.sokol.myolimp.feature_profile.data.models.RequestUserModel
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import ramble.sokol.myolimp.feature_profile.database.UserDatabase
import ramble.sokol.myolimp.feature_profile.domain.repositories.LocalUserRepository
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileEducationState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileState
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
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

    private val _user = userRepository.getUser()

    private val _state = MutableStateFlow(
        ProfileState()
    )

    private val _educationState = MutableStateFlow(ProfileEducationState())

    val educationState = _educationState.asStateFlow()

    val state = combine(_state, _user) { state, user ->
        state.copy(
            user = user,
            id = user.id,
            firstName = user.firstName,
            secondName = user.secondName,
            thirdName = user.thirdName,
            dateOfBirth = user.dateOfBirth,
            accountType = user.accountType,
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

    init {
        viewModelScope.launch {
            Log.i(TAG, "user = ${_user.first()}")

            _state.update {
                it.copy(
                    id = _user.first().id,
                    firstName = _user.first().firstName,
                    secondName = _user.first().secondName,
                    thirdName = _user.first().thirdName,
                    dateOfBirth = _user.first().dateOfBirth,
                    accountType = _user.first().accountType,
                    snils = _user.first().snils,
                    gender = _user.first().gender,
                    region = _user.first().region,
                    city = _user.first().city,
                    school = _user.first().school,
                    phone = _user.first().phone,
                    email = _user.first().email,
                    grade = _user.first().grade,
                    profileImg = _user.first().profileImg,
                    hasThird = _user.first().hasThird,
                )
            }

            Log.i(TAG, "fn - ${_state.value.secondName} - ${state.value.secondName}")
            Log.i(TAG, "fn - ${_state.value.firstName} - ${state.value.firstName}")
        }
    }

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
                _state.update {
                    it.copy(
                        secondName = event.secondName
                    )
                }
            }

            is ProfileEvent.OnThirdNameChanged -> {
                _state.update {
                    it.copy(
                        thirdName = event.thirdName
                    )
                }
            }

            is ProfileEvent.OnDobChanged -> {
                _state.update {
                    it.copy(
                        dateOfBirth = event.dob
                    )
                }
            }

            is ProfileEvent.OnGenderChanged -> {
                _state.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }

            is ProfileEvent.OnSnilsChanged -> {
                _state.update {
                    it.copy(
                        snils = event.snils
                    )
                }
            }

            is ProfileEvent.OnImgChanged -> {
                _state.update {
                    it.copy(
                        profileImg = event.uri.toString()
                    )
                }
            }

            is ProfileEvent.OnMarkerClicked -> {
                _state.update {
                    it.copy(
                        hasThird = event.hasThird
                    )
                }
            }

            is ProfileEvent.OnImgDelete -> {
                _state.update {
                    it.copy(
                        profileImg = null
                    )
                }
            }

            is ProfileEvent.OnSave -> {
                when(event.sheetName) {
                    "e" -> {
                        if(isValidData()) {
                            _state.update {
                                it.copy(
                                    region = educationState.value.region,
                                    city = educationState.value.city,
                                    school = educationState.value.school,
                                    grade = educationState.value.grade
                                )
                            }
                            viewModelScope.launch {
                                updateUserData()
                            }
                        } else Log.i(TAG,"education data isnt valid")
                    }
                    "s" -> {
                        if(isSnilsValid()) {
                            viewModelScope.launch {
                                updateUserData()
                            }
                        } else Log.i(TAG,"snils isnt valid")
                    }
                    else -> Log.i(TAG,"on save with unknown symbol")
                }
                SheetRouter.navigateTo(SheetNavigation.Empty())
            }

            is ProfileEvent.OnPersonalInfoSave -> {
                if (checkPersonalDataCorrectness()) {
                    viewModelScope.launch {
                        updateUserData()
                    }
                    SheetRouter.navigateTo(SheetNavigation.Empty())
                }
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
                _educationState.update {
                    it.copy(
                        region = event.region,
                        regionError = false
                    )
                }
                viewModelScope.launch {
                    updateCitiesList()
                    updateSchoolsList()
                }
            }

            is ProfileEvent.OnCityChanged -> {
                _educationState.update {
                    it.copy(
                        city = event.city,
                        cityError = false
                    )
                }
            }

            is ProfileEvent.OnSchoolChanged -> {
                _educationState.update {
                    it.copy(
                        school = event.school,
                        schoolError = false
                    )
                }
            }

            is ProfileEvent.OnGradeChanged -> {
                _educationState.update {
                    it.copy(
                        grade = event.grade,
                        gradeError = false
                    )
                }
            }

            is ProfileEvent.OnPhoneChanged -> {
                _state.update {
                    it.copy(
                        phone = event.phone
                    )
                }
            }

            is ProfileEvent.OnEmailChanged -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
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

            ProfileEvent.OnUploadError -> TODO()
            
            is ProfileEvent.OnAttachSheet -> {
                updateMenus()
                with(state.value) {
                    _educationState.update {
                        it.copy(
                            region = this.region ?: Region(),
                            city = this.city ?: City(),
                            school = this.school ?: School(),
                            grade = this.grade ?: 0
                        )
                    }
                }
            }
        }
    }

    private fun checkPersonalDataCorrectness(): Boolean {
        if (_state.value.firstName.isNullOrEmpty()) {
            _state.update {
                it.copy(
                    firstNameError = true
                )
            }
            return false
        }

        if (_state.value.secondName.isNullOrEmpty()) {
            _state.update {
                it.copy(
                    secondNameError = true
                )
            }
            return false
        }

        if (_state.value.thirdName.isNullOrEmpty()) {
            _state.update {
                it.copy(
                    thirdNameError = true
                )
            }
            return false
        }
        if (_state.value.dateOfBirth.isNullOrEmpty()) {
            _state.update {
                it.copy(
                    dobError = true
                )
            }
            return false
        }
        if (_state.value.snils != null && _state.value.snils!!.length != 11 || _state.value.snils != null && !_state.value.snils!!.isDigitsOnly()) {
            _state.update {
                it.copy(
                    snilsError = true
                )
            }
            return false
        }

        return true
    }

    private suspend fun updateUserData() {
        try {
            val user = RequestUserModel(
                id = state.value.id?.toInt() ?: throw Exception("no user id"),
                firstName = _state.value.firstName,
                secondName = _state.value.secondName,
                thirdName = state.value.thirdName,
                email = _state.value.email,
                dateOfBirth = _state.value.dateOfBirth,
                accountType = _state.value.accountType,
                gender = _state.value.gender,
                snils = _state.value.snils,
                regionId = _state.value.region?.number,
                schoolId = _state.value.school?.id,
                cityId = _state.value.city?.id,
                phone = _state.value.phone,
                grade = _state.value.grade,
            )

            Log.i(TAG, "user - $user")

            val response = repository.updateUser(
                user = user
            )

            Log.i(TAG, "response - ${response.body()}")

            if (response.body() != null) {
                val responseModel: ResponseUserModel? = response.body()
                userRepository.updateUser(responseModel?.toLocalUserModel()
                    ?: throw Exception("empty response user model")
                )
                with(responseModel) {
                    _state.update { state ->
                        state.copy(
                            id = id.toString(),
                            firstName = firstName,
                            secondName = secondName,
                            thirdName = thirdName,
                            dateOfBirth = dateOfBirth,
                            gender = gender,
                            snils = snils,
                            region = region?.asRegion(),
                            school = school?.asSchool(),
                            city = city?.asCity(),
                            phone = phone,
                            email = email,
                            grade = grade,
                            accountType = accountType,
                            profileImg = profileImg
                        )
                    }
                }
            }

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

    private fun updateRegionsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getRegions(
                    auth = dataStore.getToken(ACCESS_TOKEN).first()
                        ?: throw Exception("no access token"),
                    onResult = { list ->
                        Log.i(TAG, "response region list: $list")
                        if (list != null) {
                            _educationState.update {
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
                    regionId = educationState.value.region.number,
                    onResult = { list ->
                        Log.i(TAG, "response city list: $list")
                        if (list != null) {
                            _educationState.update {
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
                    regionId = educationState.value.region.number,
                    onResult = { list ->
                        Log.i(TAG, "response school list: $list")
                        if (list != null) {
                            _educationState.update {
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

        with(educationState.value) {
            if (this.regionList.find { it == this.region } == null) {
                isValid = false
                _educationState.update { it.copy(regionError = true) }
            }
            if (this.cityList.find { it == this.city } == null) {
                isValid = false
                _educationState.update { it.copy(cityError = true) }
            }
            if (this.schoolList.find { it == this.school } == null) {
                isValid = false
                _educationState.update { it.copy(schoolError = true) }
            }
            if (this.grade == 0) {
                isValid = false
                _educationState.update { it.copy(gradeError = true) }
            }
        }

        return isValid
    }

    private fun isSnilsValid(): Boolean{

        var isValid = true

        if(state.value.snils != null) {
            state.value.snils?.let {
                if(it.length != 11 && !it.isDigitsOnly()) {
                    isValid = false
                    _state.update { state -> state.copy(snilsError = true) }
                }
            }
        } else {
            isValid = false
            _state.update { state -> state.copy(snilsError = true) }
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
        _educationState.update {
            it.copy(
                regionError = false,
                cityError = false,
                schoolError = false,
                gradeError = false
            )
        }
    }
}

