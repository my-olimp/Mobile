package ramble.sokol.myolimp.feature_library.domain.repositories

import android.util.Log
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.api.LibraryApi
import ramble.sokol.myolimp.feature_library.data.models.RequestAnswerModel
import ramble.sokol.myolimp.feature_library.data.models.ResponseAnswerModel
import ramble.sokol.myolimp.feature_library.data.models.ResponseArticleModel
import ramble.sokol.myolimp.feature_library.domain.models.ResponseFavourites
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryRepository {

    private val instance = RetrofitBuilder().instance(LibraryApi::class.java)

    fun extractArticleById(
        id: Int,
        onResult: (ResponseArticleModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.extractArticleById(id).enqueue(
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

    fun checkAnswer(
        auth: String,
        id: Int,
        body: RequestAnswerModel,
        onResult: (ResponseAnswerModel?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        instance.checkAnswer(auth, id, body).enqueue(
            object : Callback<ResponseAnswerModel> {
                override fun onResponse(
                    call: Call<ResponseAnswerModel>,
                    response: Response<ResponseAnswerModel>
                ) {
                    Log.i("ViewModelArticleLibrary","answer response code is ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseAnswerModel>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

    fun addToFavourites(
        id: Int,
        onResult: (ResponseFavourites?) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        Log.i("ViewModelArticleLibrary","id $id")

        instance.addToFavourites(id).enqueue(
            object : Callback<ResponseFavourites> {
                override fun onResponse(
                    call: Call<ResponseFavourites>,
                    response: Response<ResponseFavourites>
                ) {
                    Log.i("ViewModelArticleLibrary","favourites ${response.code()}")
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResponseFavourites>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }

}
