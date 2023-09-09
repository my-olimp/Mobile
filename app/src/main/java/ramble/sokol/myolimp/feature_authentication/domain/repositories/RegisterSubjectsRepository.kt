package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_profile.data.models.UserModelEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterSubjectsRepository : KoinComponent {

    private val instance = RetrofitBuilder().instance(RegistrationApi::class.java)

    fun getSchools(
        auth: String,
        onResult: (List<RequestSubjectModel>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getSubjects(auth).enqueue(
            object : Callback<List<RequestSubjectModel>> {
                override fun onResponse(
                    call: Call<List<RequestSubjectModel>>,
                    response: Response<List<RequestSubjectModel>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<RequestSubjectModel>>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun updateSubjects(
        auth: String,
        subjects: RequestSubjects,
        onResult: (UserModelEntity?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateSubjects(auth, subjects).enqueue(
            object : Callback<UserModelEntity> {
                override fun onResponse(
                    call: Call<UserModelEntity>,
                    response: Response<UserModelEntity>
                ) {
                    Log.i("ViewModelRegisterSubjects", "code - ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserModelEntity>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}
