package ramble.sokol.myolimp.utils.interceptors

import android.content.Context
import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import ramble.sokol.myolimp.feature_profile.data.Constants.COOKIES
import ramble.sokol.myolimp.utils.CookiesDataStore

class ReceivedCookiesInterceptor(
    context: Context
) : Interceptor {

    companion object {
        private const val TAG = "InterceptorReceivedCookies"
    }

    private val context: Context
    private val cookiesDataStore = CookiesDataStore(context = context)
    private val scope = MainScope()

    init {
        this.context = context
    }

    @Throws(IOException::class)
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val originalResponse: Response = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

            scope.launch {
                var cookie = cookiesDataStore.getCookies(COOKIES)

                Log.i(TAG, "begin - $cookie")

                for (header in originalResponse.headers("Set-Cookie")) {

                    if(header.contains("refresh")) {
                        cookie = header.substringBefore(";")
                    }

                    Log.i(TAG, header)
                }

                if (cookie != null) {
                    cookiesDataStore.setCookies(
                        value = cookie,
                        key = COOKIES
                    )
                }

                Log.i(TAG, "end - $cookie")
            }

        }

        return originalResponse
    }
}
