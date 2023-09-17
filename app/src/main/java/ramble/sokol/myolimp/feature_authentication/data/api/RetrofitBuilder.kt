package ramble.sokol.myolimp.feature_authentication.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.feature_profile.data.Constants.BASE_URL
import ramble.sokol.myolimp.utils.interceptors.AuthorizedInterceptor
import ramble.sokol.myolimp.utils.interceptors.NetworkConnectionInterceptor
import ramble.sokol.myolimp.utils.interceptors.ReceivedCookiesInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder : KoinComponent {

    private val client = OkHttpClient.Builder()
//        .addInterceptor(SetCookiesInterceptor(context = context))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))

        // get cookie
        .addInterceptor(ReceivedCookiesInterceptor())

        // if there is network connection
        .addInterceptor(NetworkConnectionInterceptor())

        // is user authenticated
        .addInterceptor(AuthorizedInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> instance(service: Class<T>): T {
        return retrofit.create(service)
    }

}
