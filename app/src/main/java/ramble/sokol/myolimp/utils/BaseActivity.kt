package ramble.sokol.myolimp.utils

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent


open class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Instabug.Builder(application, "d71f4091bae235ff0a2e104df76605db")
            .setInvocationEvents(
                InstabugInvocationEvent.SHAKE
            )
            .build()
    }
}
