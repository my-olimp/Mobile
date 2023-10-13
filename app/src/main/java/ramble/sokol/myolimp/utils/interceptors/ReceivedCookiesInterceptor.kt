package ramble.sokol.myolimp.utils.interceptors

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import org.koin.core.component.KoinComponent
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore
import ramble.sokol.myolimp.feature_authentication.domain.repositories.CodeDataStore.Companion.COOKIES

class ReceivedCookiesInterceptor : Interceptor, KoinComponent {

    companion object {
        private const val TAG = "InterceptorReceivedCookies"
    }

    private val codeDataStore = CodeDataStore()

    @Throws(IOException::class)
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val originalResponse: Response = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

            runBlocking {
                var cookie = codeDataStore.getToken(COOKIES).first()

                for (header in originalResponse.headers("Set-Cookie")) {

                    if(header.contains("refresh")) {
                        cookie = header.substringBefore(";")
                    }

                    Log.i(TAG, header)
                }

                codeDataStore.setToken(
                    value = cookie ?: throw Exception("no cookies"),
                    key = COOKIES
                )

                Log.i(TAG, "cookie - ${codeDataStore.getToken(COOKIES).first()}")
            }
        }

        Log.i(TAG, "returning")

        return originalResponse
    }
}
