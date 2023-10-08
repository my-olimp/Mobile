package ramble.sokol.myolimp.feature_library.data.repository

import android.graphics.pdf.PdfDocument.Page
import org.json.JSONArray
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.remote.LibraryAPI
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.database.UserDatabase

class LibraryRepositoryImpl(
    val database: UserDatabase
) {

    private val libraryInstance = RetrofitBuilder().instance(LibraryAPI::class.java)

    suspend fun getArticles(
        page: Int,
        query: String,
        isShowFavourites: Boolean,
        onSuccess: (List<ArticleModel>) -> Unit,
        onError: (String?) -> Unit
    ) {

        val result = libraryInstance.getArticles(
            page = page,
            love = if (isShowFavourites) 1 else 0,
            query = query
        )

        if (result.isSuccessful && result.body() != null) {
            onSuccess(result?.body()?.articles?.map {
                it.toArticleModel()
            } ?: emptyList())
        } else {
            onError(result.message())
        }
    }

    suspend fun getUserSubjects(): List<String> {
        val jsonArray = JSONArray(database.getUserDao().getUserSubjects())
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}
