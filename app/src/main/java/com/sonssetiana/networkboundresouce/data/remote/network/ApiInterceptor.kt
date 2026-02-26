package com.sonssetiana.networkboundresouce.data.remote.network

import com.sonssetiana.networkboundresouce.configs.Configs
import com.sonssetiana.networkboundresouce.configs.Env
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(Configs.AUTHORIZATION, "Bearer ${Env.API_KEY}")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}