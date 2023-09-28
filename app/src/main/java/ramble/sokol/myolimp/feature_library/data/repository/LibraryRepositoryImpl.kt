package ramble.sokol.myolimp.feature_library.data.repository

import org.json.JSONArray
import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.remote.LibraryAPI
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance
import ramble.sokol.myolimp.feature_profile.database.UserDatabase

class LibraryRepositoryImpl(val database: UserDatabase) {
    private val libraryInstance = RetrofitBuilder().instance(LibraryAPI::class.java)

    suspend fun getArticles(
        auth: String
    ): List<ArticleModel> {
        return libraryInstance.getArticles(auth).articles.map { articleEntity ->
            articleEntity.toArticleModel()
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