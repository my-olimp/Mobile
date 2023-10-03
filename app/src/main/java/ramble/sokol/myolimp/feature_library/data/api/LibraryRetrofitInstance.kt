package ramble.sokol.myolimp.feature_library.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ramble.sokol.myolimp.feature_profile.data.Constants
import ramble.sokol.myolimp.feature_profile.data.api.ProfileApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryRetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: ProfileApi by lazy {
            retrofit.create(ProfileApi::class.java)
        }
    }
}