package ramble.sokol.myolimp.feature_authentication.data.api

import android.content.Context
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.utils.interceptors.ReceivedCookiesInterceptor
import ramble.sokol.myolimp.utils.interceptors.SetCookiesInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(
    context: Context
) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(SetCookiesInterceptor(context = context))
        .addInterceptor(ReceivedCookiesInterceptor(context = context))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> instance(service: Class<T>): T {
        return retrofit.create(service)
    }

}
