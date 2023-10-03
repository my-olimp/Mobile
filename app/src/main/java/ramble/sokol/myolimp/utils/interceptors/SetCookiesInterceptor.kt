package ramble.sokol.myolimp.utils.interceptors

import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.COOKIES

class SetCookiesInterceptor : Interceptor {

    companion object {
        private const val TAG = "InterceptorSetCookies"
    }

    private val codeDataStore = CodeDataStore()
    private val scope = MainScope()

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        scope.launch {
            val cookie = codeDataStore.getToken(COOKIES).first()

            Log.i(TAG, "begin setting - $cookie")

            if (cookie != null) {
                builder.addHeader("Cookie", cookie)
            }

        }

        return chain.proceed(builder.build())
    }
}
