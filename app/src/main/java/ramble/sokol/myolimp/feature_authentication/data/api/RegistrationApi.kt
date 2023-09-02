package ramble.sokol.myolimp.feature_authentication.data.api

import ramble.sokol.myolimp.feature_authentication.data.models.UserMainDataModel
import ramble.sokol.myolimp.feature_profile.data.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH

interface RegistrationApi {

    @Headers("Content-Type: application/json")
    @PATCH("user/")
     fun updateMainUserData(
        @Header("Authorization") auth: String,
        @Body data: UserMainDataModel
    ) : Response<UserModel>
}