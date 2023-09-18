package ramble.sokol.myolimp.feature_library.domain.states

import ramble.sokol.myolimp.feature_library.data.models.ResponseArticleModel
import ramble.sokol.myolimp.feature_library.domain.models.ArticleModel

data class ArticleState(
    val article: ArticleModel = ArticleModel()
)