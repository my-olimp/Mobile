package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.util.Log
import org.koin.core.component.KoinComponent
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjects
import ramble.sokol.myolimp.feature_profile.data.models.ResponseUserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterSubjectsRepository : KoinComponent {

    private val instance = RetrofitBuilder().instance(RegistrationApi::class.java)

    fun getSubjects(
        onResult: (List<String>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getSubjects().enqueue(
            object : Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }

    fun updateSubjects(
        subjects: RequestSubjects,
        onResult: (ResponseUserModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.updateSubjects(subjects).enqueue(
            object : Callback<ResponseUserModel> {
                override fun onResponse(
                    call: Call<ResponseUserModel>,
                    response: Response<ResponseUserModel>
                ) {
                    Log.i("ViewModelRegisterSubjects", "code - ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseUserModel>, t: Throwable) {
                    onError(t)
                }
            }
        )
    }
}
