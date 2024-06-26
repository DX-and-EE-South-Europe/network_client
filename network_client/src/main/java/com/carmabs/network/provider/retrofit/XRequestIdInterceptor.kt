package com.bimbaylola.network.provider.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class XRequestIdInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
                .header("x-request-id", UUID.randomUUID().toString())
                .build()

        return chain.proceed(request)

    }

}