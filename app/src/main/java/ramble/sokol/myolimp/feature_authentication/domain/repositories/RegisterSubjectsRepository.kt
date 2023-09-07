package ramble.sokol.myolimp.feature_authentication.domain.repositories

import android.content.Context
import ramble.sokol.myolimp.feature_authentication.data.api.RegistrationApi
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_authentication.data.models.RequestSubjectModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterSubjectsRepository (
    val context: Context
) {

    private val instance = RetrofitBuilder(context = context).instance(RegistrationApi::class.java)

    fun getSchools(
        auth: String,
        onResult: (List<String>?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.getSubjects(auth).enqueue(
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
}
