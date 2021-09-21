package com.blazze.albumapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val HTTP_CLIENT_CONNECT_TIMEOUT = 1
    private const val HTTP_CLIENT_READ_TIMEOUT = 30
    private const val WS_SERVER_URL = "https://jsonplaceholder.typicode.com"
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
                .connectTimeout(HTTP_CLIENT_CONNECT_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .readTimeout(HTTP_CLIENT_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(WS_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

            return retrofit
        }

}