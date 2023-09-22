package ramble.sokol.myolimp.feature_library.data.repository

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
        return database.getUserDao().getUserSubjects()
    }
}