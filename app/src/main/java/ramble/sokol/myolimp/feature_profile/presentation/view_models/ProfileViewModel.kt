package ramble.sokol.myolimp.feature_profile.presentation.view_models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
import ramble.sokol.myolimp.feature_profile.domain.repositories.ProfileRepository
import ramble.sokol.myolimp.feature_profile.domain.states.ImgState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileContactsState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileEducationState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfilePersonalState
import ramble.sokol.myolimp.feature_profile.domain.states.ProfileState
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetNavigation
import ramble.sokol.myolimp.feature_profile.navigation_sheets.SheetRouter
import ramble.sokol.myolimp.feature_profile.utils.ProfileEvent
import ramble.sokol.myolimp.utils.BaseViewModel
import ramble.sokol.myolimp.utils.exceptions.ViewModelExceptions
import java.io.File
import java.io.FileOutputStream
import java.net.UnknownHostException

class ProfileViewModel : BaseViewModel<ProfileState>(ProfileState()) {

    private val dataStore = CodeDataStore()

    private val repository = ProfileRepository()

    private val _user = userRepository.getUser()

    /*Image state*/
    private val _imgState = MutableStateFlow(ImgState())
    val imgState = _imgState.asStateFlow()
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
                    hasThird = _user.first().hasThird,
                    isLoading = false
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

            /*TODO REMOVE AFTER TESTING*/
            is ProfileEvent.OnChangeType -> {
                _state.update {
                    it.copy(
                        accountType = event.newType
                    )
                }
            }
            /**/

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
                _imgState.update {
                    it.copy(
                        profileImg = event.uri
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
                if (event.region != Region()) {
                    _educationState.update {
                        it.copy(
                            region = event.region,
                            regionError = false,
                            isLoading = true,
                            cityList = emptyList(),
                            schoolList = emptyList()
                        )
                    }
                    viewModelScope.launch {
                        updateCitiesList()
                        updateSchoolsList()
                    }
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

            is ProfileEvent.OnDeleteImg -> {
                launchIO {
                    updateLoader(true)

                    deleteImg(
                        onResult = {
                            SheetRouter.navigateTo(SheetNavigation.Empty())
                        }
                    )

                    updateLoader(false)

                    _imgState.update {
                        it.copy(
                            isImgChanged = true
                        )
                    }
                }
            }

            is ProfileEvent.OnContactsInfoSave -> {
                if(isContactsValid()) {
                    _state.update {
                        it.copy(
                            email = contactsState.value.email,
                            phone = contactsState.value.phone
                        )
                    }
                    launchIO {
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

                    updateLoader(true)

                    uploadImg(
                        onResult = {
                            SheetRouter.navigateTo(SheetNavigation.Empty())
                            _imgState.update {
                                it.copy(
                                    profileImg = null,
                                    isImgChanged = true
                                )
                            }
                        }
                    )
                    updateLoader(false)
                }
            }

            is ProfileEvent.OnImgUpdated -> {
                _imgState.update {
                    it.copy(
                        isImgChanged = false
                    )
                }
            }

            is ProfileEvent.OnLogOut -> {

                updateLoader(true)

                launchIO {
                    userRepository.deleteUsers()

                    Log.i(TAG, "user - ${userRepository.getUser().firstOrNull()}")

                    repository.logOut(
                        cookie = dataStore.getToken(COOKIES).firstOrNull()
                            ?: throw ViewModelExceptions.Network,
                        onResult = {
                            runBlocking(Dispatchers.Main) {
                                dataStore.deleteToken()

                                updateLoader(false)

                                event.navigator.popBackStack()
                                event.navigator.navigate(BeginAuthenticationScreenDestination)

                                Log.i(TAG, "completed")

                            }
                        },
                        onError = {
                            updateLoader(false)
                            castError()
                            Log.i(TAG, "error occurred - $it")
                        }
                    )

                }
            }

            is ProfileEvent.OnEducationSheetAttach -> {
                if(event.region == Region())updateRegionsList()
                else updateMenus()
            }
        }
    }

    private suspend fun deleteImg(
        onResult: () -> Unit
    ) {
        repository.deleteImg(
            onError = {
                Log.i(TAG, "error - $it")
            },
            onResult = {
                Log.i(TAG, "successfully deleted")
                onResult()
            }
        )
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


    private fun updatePersonalInfo() {
        launchIO {
            val requestModel = RequestUserModel(
                firstName = state.value.firstName,
                secondName = state.value.secondName,
                thirdName = state.value.thirdName,
                gender = state.value.gender,
                snils = state.value.snils,
                dateOfBirth = state.value.dateOfBirth
            )
            Log.i(TAG, "on update personal info request - $requestModel")

            repository.updateUser(
                user = requestModel,
                onResult = { response ->
                    Log.i(TAG, "personal info response: $response")
                    if (response != null) {
                        updateDatabase(response)
                    }
                },
                onError = {
                    castError(ViewModelExceptions.Network)
                    Log.i(TAG, "update personal info error ${it.message}")
                }
            )
            Log.i(TAG,"on update personal info response - ${response.body()}")

            if(response.body() != null) {
                updateDatabase(response.body() as ResponseUserModel)
            }
        } catch (e: Exception) {
            if(e is UnknownHostException) castError(ViewModelExceptions.Network)
            Log.i(TAG,"error ${e.message}")
        }
    }

    private fun updateEducation() {
        launchIO {
            val requestModel = RequestUserModel(
                regionId = state.value.region?.number,
                schoolId = state.value.school?.id,
                cityId = state.value.city?.id,
                grade = state.value.grade
            )

            Log.i(TAG, "on update education info request - $requestModel")

            repository.updateUser(
                user = requestModel,
                onResult = { response ->
                    Log.i(TAG, "personal info response: $response")
                    if (response != null) {
                        updateDatabase(response)
                    }
                },
                onError = {
                    castError(ViewModelExceptions.Network)
                    Log.i(TAG, "update personal info error ${it.message}")
                }
            )
        }
    }

    private fun updateContacts() {
        launchIO {
            val requestModel = RequestUserModel(
                email = state.value.email,
                phone = state.value.phone
            )

            Log.i(TAG, "on update contacts info request - $requestModel")

            repository.updateUser(
                user = requestModel,
                onResult = { response ->
                    Log.i(TAG, "personal info response: $response")
                    if (response != null) {
                        updateDatabase(response)
                    }
                },
                onError = {
                    castError(ViewModelExceptions.Network)
                    Log.i(TAG, "update personal info error ${it.message}")
                }
            )
        }
    }

    private fun updateDatabase(response: ResponseUserModel) {
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
                    accountType = accountType
                )
            }
        }
        launchIO {
            userRepository.updateUser(
                user = response.toLocalUserModel()
            )
        }
    }

    private suspend fun uploadImg(
        onResult: () -> Unit
    ) {
        try {

            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(
                imgState.value.profileImg ?: Uri.EMPTY)
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

            repository.uploadImg(
                imageBody = body,
                onResult = onResult,
                onError = { throw it }
            )

            Log.i(TAG, "response - success")

        } catch (ex: Exception) {
            if(ex is UnknownHostException)castError(ViewModelExceptions.Network)
            Log.i(TAG, "error - $ex")
        }
    }

    private fun updateRegionsList() {
        updateEducationLoading(true)
        launchIO {
            repository.getRegions(
                auth = dataStore.getToken(ACCESS_TOKEN).first()
                    ?: throw ViewModelExceptions.Network,
                onResult = { list ->
                    Log.i(TAG, "response region list: $list")
                    if (list != null) {
                        _educationState.update {
                            it.copy(
                                regionList = list.asListRegion(),
                                cityList = emptyList(),
                                schoolList = emptyList(),
                                isLoading = false
                            )
                        }
                    }
                },
                onError = {
                    Log.i(TAG, "region exception $it")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

    private fun updateCitiesList() {
        updateEducationLoading(true)
        launchIO {
            repository.getCities(
                auth = dataStore.getToken(ACCESS_TOKEN).first()
                    ?: throw ViewModelExceptions.Network,
                regionId = educationState.value.region.number,
                onResult = { list ->
                    Log.i(TAG, "response city list: $list")
                    if (list != null) {
                        _educationState.update {
                            it.copy(cityList = list.asListCity())
                        }
                        if (educationState.value.schoolList.isNotEmpty()) updateEducationLoading(false)
                    }
                },
                onError = {
                    Log.i(TAG, "city exception $it")
                    castError(ViewModelExceptions.Network)
                }
            )
        }
    }

    private fun updateSchoolsList() {
        updateEducationLoading(true)
        launchIO {
            repository.getSchools(
                auth = dataStore.getToken(ACCESS_TOKEN).first()
                    ?: throw ViewModelExceptions.Network,
                regionId = educationState.value.region.number,
                onResult = { list ->
                    Log.i(TAG, "response school list: $list")
                    if (list != null) {
                        _educationState.update {
                            it.copy(schoolList = list.asListSchool())
                        }
                        if (educationState.value.cityList.isNotEmpty()) updateEducationLoading(false)
                    }
                },
                onError = {
                    Log.i(TAG, "school exception $it")
                    castError(ViewModelExceptions.Network)
                }
            )
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
        launchIO {

            _educationState.update {
                it.copy(
                    regionError = false,
                    cityError = false,
                    schoolError = false,
                    gradeError = false
                )
            }

            updateRegionsList()
            if (state.value.region != Region()) {
                updateCitiesList()
                updateSchoolsList()
            }
        }
    }

    private fun updateEducationLoading(boolean: Boolean) {
        _educationState.update { it.copy(isLoading = boolean) }
    }
}
