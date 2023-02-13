package com.enterprise.agino.common.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UserAgentInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("User-Agent", "me@gmail.com")
        return chain.proceed(builder.build())
    }
}