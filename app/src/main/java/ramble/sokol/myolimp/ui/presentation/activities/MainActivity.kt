package ramble.sokol.myolimp.ui.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import ramble.sokol.myolimp.NavGraphs
import ramble.sokol.myolimp.utils.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }
}
