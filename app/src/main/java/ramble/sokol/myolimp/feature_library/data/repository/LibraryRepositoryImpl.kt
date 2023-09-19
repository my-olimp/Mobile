package ramble.sokol.myolimp.feature_library.data.repository

import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.remote.LibraryAPI
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import ramble.sokol.myolimp.feature_profile.data.api.ProfileRetrofitInstance

class LibraryRepositoryImpl {
    private val libraryInstance = RetrofitBuilder().instance(LibraryAPI::class.java)
    private val profileInstance = ProfileRetrofitInstance().instance(ProfileApi::class.java)

    suspend fun refreshToken(cookie: String)
            = profileInstance.refreshToken(cookie)

    suspend fun getArticles(
        auth: String
    ): List<ArticleModel> {
        return libraryInstance.getArticles(auth).articles.map { articleEntity ->
            articleEntity.toArticleModel()
        }
    }
}