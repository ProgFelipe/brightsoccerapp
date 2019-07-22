package com.ideaware.brightsoccer.api

import com.ideaware.brightsoccer.BuildConfig
import com.ideaware.brightsoccer.service.SoccerMatchesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    companion object {
        private var retrofit: Retrofit? = null

        @Synchronized
        private fun getRetrofitInstance(): Retrofit {
            return retrofit ?: retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build()
        }

        private fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .callTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()
        }
    }

    fun getService(): SoccerMatchesService {
        return getRetrofitInstance().create(SoccerMatchesService::class.java)
    }
}