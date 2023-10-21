package ramble.sokol.myolimp.ui.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Alignment
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.utils.BaseActivity

class MainActivity : BaseActivity() {
    @OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                engine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                    defaultAnimationsForNestedNavGraph = mapOf(
                        NavGraphs.root to NestedNavGraphDefaultAnimations(
                            enterTransition = {
                                fadeIn(animationSpec = tween(2000))
                            },
                            exitTransition = {
                                fadeOut(animationSpec = tween(2000))
                            }
                        ),
                    )
                )
            )
        }
    }
}
