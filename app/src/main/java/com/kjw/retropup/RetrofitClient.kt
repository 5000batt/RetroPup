package com.kjw.retropup

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS) // 서버와 최초 연결 대기 시간
        .readTimeout(15, TimeUnit.SECONDS)    // 연결 후 데이터 수신 대기 시간
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val dogApi: DogApi = retrofit.create()
}