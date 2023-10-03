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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileContactsState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileEducationState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfilePersonalState
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

    /*General state*/
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    /**/

    /*Education sheet state*/
    private val _educationState = MutableStateFlow(ProfileEducationState())
    val educationState = _educationState.asStateFlow()
    /**/

    /*Personal info sheet state*/
    private val _personalState = MutableStateFlow(ProfilePersonalState())
    val personalState = _personalState.asStateFlow()
    /**/

    /*Contacts info sheet state*/
    private val _contactsState = MutableStateFlow(ProfileContactsState())
    val contactsState = _contactsState.asStateFlow()
    /**/

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
                    isLoaded = true
                )
            }
            _educationState.update {
                it.copy(
                    region = state.value.region ?: Region(),
                    city = state.value.city ?: City(),
                    school = state.value.school ?: School(),
                    grade = state.value.grade ?: 0
                )
            }
            _personalState.update {
                it.copy(
                    firstName = state.value.firstName,
                    secondName = state.value.secondName,
                    thirdName = state.value.thirdName,
                    dateOfBirth = state.value.dateOfBirth,
                    gender = state.value.gender,
                    snils = state.value.snils
                )
            }
            _contactsState.update {
                it.copy(
                    email = state.value.email,
                    phone = state.value.phone
                )
            }
        }
    }

    fun onEvent (
        event: ProfileEvent
    ) {
        when (event) {
            is ProfileEvent.OnFirstNameChanged -> {
                _personalState.update {
                    it.copy(
                        firstName = event.firstName,
                        firstNameError = false
                    )
                }
            }

            is ProfileEvent.OnSecondNameChanged -> {
                _personalState.update {
                    it.copy(
                        secondName = event.secondName,
                        secondNameError = false
                    )
                }
            }

            is ProfileEvent.OnThirdNameChanged -> {
                _personalState.update {
                    it.copy(
                        thirdName = event.thirdName,
                        thirdNameError = false
                    )
                }
            }

            is ProfileEvent.OnDobChanged -> {
                _personalState.update {
                    it.copy(
                        dateOfBirth = event.dob,
                        dobError = false
                    )
                }
            }

            is ProfileEvent.OnGenderChanged -> {
                _personalState.update {
                    it.copy(
                        gender = event.gender
                    )
                }
            }

            is ProfileEvent.OnSnilsChanged -> {
                _personalState.update {
                    it.copy(
                        snils = event.snils,
                        snilsError = false
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
                _personalState.update {
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


            is ProfileEvent.OnPhoneChanged -> {
                _contactsState.update {
                    it.copy(
                        phone = event.phone,
                        phoneError = false
                    )
                }
            }

            is ProfileEvent.OnEmailChanged -> {
                _contactsState.update {
                    it.copy(
                        email = event.email,
                        emailError = false
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

            is ProfileEvent.OnEducationInfoSave -> {
                if (isValidData()) {
                    _state.update {
                        it.copy(
                            region = educationState.value.region,
                            city = educationState.value.city,
                            school = educationState.value.school,
                            grade = educationState.value.grade
                        )
                    }
                    viewModelScope.launch {
                        updateEducation()
                    }
                    SheetRouter.navigateTo(SheetNavigation.Empty())
                } else Log.i(TAG, "education data isn't valid")
            }

            is ProfileEvent.OnContactsInfoSave -> {
                if(isContactsValid()) {
                    _state.update {
                        it.copy(
                            email = contactsState.value.email,
                            phone = contactsState.value.phone
                        )
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        updateContacts()
                    }
                    SheetRouter.navigateTo(SheetNavigation.Empty())
                } else Log.i(TAG,"contacts data isn't valid")
            }

            is ProfileEvent.OnPersonalInfoSave -> {
                if (checkPersonalDataCorrectness()) {
                    with(personalState.value) {
                        _state.update {
                            it.copy(
                                firstName = firstName,
                                secondName = secondName,
                                thirdName = thirdName,
                                dateOfBirth = dateOfBirth,
                                gender = gender,
                                snils = snils
                            )
                        }
                    }
                    viewModelScope.launch {
                        updatePersonalInfo()
                    }
                    SheetRouter.navigateTo(SheetNavigation.Empty())
                } else Log.i(TAG,"personal info isn't valid")
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

            is ProfileEvent.OnUploadError -> TODO()

            is ProfileEvent.OnEducationSheetAttach -> {
                if(event.region == Region())updateRegionsList()
                else updateMenus()
            }
        }
    }

    private fun checkPersonalDataCorrectness(): Boolean {
        var isCorrect = true

        if(personalState.value.firstName.isNullOrEmpty()) {
            _personalState.update {
                it.copy(
                    firstNameError = true
                )
            }
            isCorrect = false
        }
        if(personalState.value.secondName.isNullOrEmpty()) {
            _personalState.update {
                it.copy(
                    secondNameError = true
                )
            }
            isCorrect = false
        }
        if(personalState.value.thirdName.isNullOrEmpty()) {
            _personalState.update {
                it.copy(
                    thirdNameError = true
                )
            }
            isCorrect = false
        }
            if(personalState.value.dateOfBirth.isNullOrEmpty()) {
            _personalState.update {
                it.copy(
                    dobError = true
                )
            }
            isCorrect = false
        }
        if(!isSnilsValid()) {
            isCorrect = false
        }

        return isCorrect


    }


    private suspend fun updatePersonalInfo() {
        try {

            val requestModel = RequestUserModel(
                firstName = state.value.firstName,
                secondName = state.value.secondName,
                thirdName = state.value.thirdName,
                gender = genderCheck(),
                snils = state.value.snils,
                dateOfBirth = state.value.dateOfBirth
            )

            Log.i(TAG,"on update personal info request - $requestModel")

            val response = repository.updateUser(
                user = requestModel
            )
            Log.i(TAG,"on update personal info response - ${response.body()}")

            if(response.body() != null) {
                updateDatabase(response.body() as ResponseUserModel)
            }
        } catch (e: Exception) {
            Log.i(TAG,"throwed ${e.message}")
        }
    }

    private suspend fun updateEducation() {
        try {
            val requestModel = RequestUserModel (
                regionId = state.value.region?.number,
                schoolId = state.value.school?.id,
                cityId = state.value.city?.id,
                grade = state.value.grade
            )

            Log.i(TAG,"on update education info request - $requestModel")

            val response = repository.updateUser(
                user = requestModel
            )

            Log.i(TAG,"on update education info response - ${response.body()}")

            if(response.body() != null) {
                updateDatabase(response.body() as ResponseUserModel)
            }

        } catch (e: Exception) {
            Log.i(TAG,"throwed ${e.message}")
        }
    }

    private suspend fun updateContacts() {
        try {
            val requestModel = RequestUserModel (
                email = state.value.email,
                phone = state.value.phone
            )

            Log.i(TAG,"on update contacts info request - $requestModel")

            val response = repository.updateUser(
                user = requestModel
            )

            Log.i(TAG,"on update contacts info response - ${response.body()}")

            if(response.body() != null) {
                updateDatabase(response.body() as ResponseUserModel)
            }
        } catch(e: Exception) {
            Log.i(TAG,"throwed ${e.message}")
        }
    }

    private suspend fun updateDatabase(response: ResponseUserModel) {
        with(response) {
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
        userRepository.updateUser(
            user = response.toLocalUserModel()
        )
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

    private fun isContactsValid() : Boolean {
        var isValid = true
        with(contactsState.value) {
            if(phone == null || phone.length != 11) {
                _contactsState.update {
                    it.copy(phoneError = true)
                }
                isValid = false
            }
            if(email.isNullOrEmpty()) {
                _contactsState.update {
                    it.copy(emailError = true)
                }
                isValid = false
            }
        }
        return isValid
    }

    private fun isSnilsValid(): Boolean{

        var isValid = true

        if(personalState.value.snils != null) {
            personalState.value.snils?.let {
                if(it.length != 11 || !it.isDigitsOnly()) {
                    isValid = false
                    _personalState.update { state -> state.copy(snilsError = true) }
                }
            }
        } else {
            isValid = false
            _personalState.update { state -> state.copy(snilsError = true) }
        }

        return isValid
    }

    private fun updateMenus() {
        viewModelScope.launch(Dispatchers.IO) {

            _educationState.update {
                it.copy(
                    regionError = false,
                    cityError = false,
                    schoolError = false,
                    gradeError = false
                )
            }

            updateRegionsList()
            if (state.value.region?.name != "") {
                updateCitiesList()
                updateSchoolsList()
            }
        }
    }
    private fun genderCheck() : String? {
        return when(state.value.gender?.lowercase()) {
            "мужской" -> "m"
            "женский" -> "f"
            else -> null
        }
    }
}

