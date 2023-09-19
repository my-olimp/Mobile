package ramble.sokol.myolimp.feature_library.domain.repositories

import android.util.Log
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.api.LibraryApi
import ramble.sokol.myolimp.feature_library.data.models.ResponseArticleModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryRepository {

    private val instance = RetrofitBuilder().instance(LibraryApi::class.java)

    fun extractArticleById(
        auth: String,
        id: Int,
        onResult: (ResponseArticleModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.extractArticleById(auth,id).enqueue(
            object: Callback<ResponseArticleModel> {
                override fun onResponse(
                    call: Call<ResponseArticleModel>,
                    response: Response<ResponseArticleModel>
                ) {
                    Log.i("ViewModelArticleLibrary","response code: ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseArticleModel>, t: Throwable) {
                    Log.i("ViewModelArticleLibrary","response exception: ${t.message}")
                    onError(t)
                }

            }
        )
    }

}