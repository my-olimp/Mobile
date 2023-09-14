package ramble.sokol.myolimp.utils.interceptors

import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ramble.sokol.myolimp.feature_profile.data.Constants.COOKIES
import ramble.sokol.myolimp.utils.CookiesDataStore

class SetCookiesInterceptor : Interceptor {

    companion object {
        private const val TAG = "InterceptorSetCookies"
    }

    private val cookiesDataStore = CookiesDataStore()
    private val scope = MainScope()

    override fun intercept(chain: Interceptor.Chain): Response {
        val  builder: Request.Builder = chain.request().newBuilder()

        scope.launch {
            val cookie = cookiesDataStore.getCookies(COOKIES)

            Log.i(TAG, "begin setting - $cookie")

            if (cookie != null) {
                builder.addHeader("Cookie", cookie)
            }

        }

        return chain.proceed(builder.build())
    }
}
