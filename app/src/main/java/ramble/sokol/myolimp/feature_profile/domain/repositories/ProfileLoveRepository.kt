package ramble.sokol.myolimp.feature_profile.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_profile.data.api.ProfileLoveApi

class ProfileLoveRepository {
    private val instance = RetrofitBuilder().instance(ProfileLoveApi::class.java)

    suspend fun extractArticles(
        page: Int,
        love: Int,
        query: String
    ) = instance.getArticles(page, love, query)

    suspend fun extractSubjects(auth: String) = instance.getSubjects(auth)
}