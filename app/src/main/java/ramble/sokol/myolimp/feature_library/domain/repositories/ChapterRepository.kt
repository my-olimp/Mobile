package ramble.sokol.myolimp.feature_library.domain.repositories

import ramble.sokol.myolimp.feature_authentication.data.api.RetrofitBuilder
import ramble.sokol.myolimp.feature_library.data.api.ChapterApi
import ramble.sokol.myolimp.feature_library.data.results.GetArticlesResult

class ChapterRepository {

    private val instance = RetrofitBuilder().instance(ChapterApi::class.java)

    suspend fun getArticles(
        subject: String,
        page: Int,
        isFavourites: Boolean,
        query: String,
        onSuccess: (GetArticlesResult) -> Unit,
        onError: (String?) -> Unit
    ) {

        val result = instance.getArticles(
            page = page,
            subject = subject,
            query = query,
            love = if (isFavourites) 1 else 0
        )

        if (result.isSuccessful && result.body() != null) {
            onSuccess(result.body()!!)
        } else {
            onError(result.message())
        }
    }

}
