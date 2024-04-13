package rs.raf.rma.klk.catalist.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import rs.raf.rma.klk.catalist.networking.serialization.AppJson

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor {
        val updatedRequest = it.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("x-api-key", "live_LBEbJAu7sjezw0JOgoJGd3SjUEC0pWijv2fcUZpp7einLufpUrBz4savSVExksSz")
            .build()
        it.proceed(updatedRequest)
    }
    .addInterceptor (
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    )
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.thecatapi.com/v1/")
    .client(okHttpClient)
    .addConverterFactory(AppJson.asConverterFactory("application/json".toMediaType()))
    .build()