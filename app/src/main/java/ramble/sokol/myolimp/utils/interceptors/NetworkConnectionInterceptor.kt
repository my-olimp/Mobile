package ramble.sokol.myolimp.utils.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ramble.sokol.myolimp.utils.exceptions.NetworkConnectivityException
import java.io.IOException


class NetworkConnectionInterceptor : Interceptor, KoinComponent {

    private val context by inject<Context>()

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi or cellular
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        }

    @Throws(IOException::class)
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {

        val response = chain.proceed(chain.request())

        if (!isConnected) {
            throw NetworkConnectivityException()
        }

        return response
    }
}
