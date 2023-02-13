package com.enterprise.agino.common.interceptors

import com.enterprise.agino.common.exceptions.APIException
import com.enterprise.agino.common.exceptions.ServerError
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val result = chain.proceed(builder.build())

        if (!result.isSuccessful) {
            if (result.code >= 500)
                throw ServerError("Server Error")
            else
                throw APIException("Unknown Error")
        }

        return result
    }
}