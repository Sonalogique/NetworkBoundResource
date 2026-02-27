package com.sonssetiana.networkboundresouce.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sonssetiana.networkboundresouce.configs.Configs
import com.sonssetiana.networkboundresouce.data.remote.network.ApiInterceptor
import com.sonssetiana.networkboundresouce.data.remote.service.MovieServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = module {
    single {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        ChuckerInterceptor.Builder(androidContext())
            .collector(ChuckerCollector(androidContext()))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

    }

    single {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    single { Gson() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ChuckerInterceptor>())
            .addInterceptor(ApiInterceptor())
            .callTimeout(Configs.TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(Configs.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Configs.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Configs.TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Configs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<MovieServices> {
        get<Retrofit>().create(MovieServices::class.java)
    }
}