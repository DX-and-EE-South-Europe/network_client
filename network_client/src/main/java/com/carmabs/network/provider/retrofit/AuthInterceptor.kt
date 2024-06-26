package com.bimbaylola.network.provider.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private var token: String? = null, private val prefixToken: String = "") :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        token?.also {
            if(it.isNotEmpty()) {
                request = request.newBuilder()
                    .header("Authorization", prefixToken + it)
                    .build()
            }
        }

        return chain.proceed(request)
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun removeToken() {
        this.token = null
    }

    fun getCurrentToken(): String? {
        return this.token
    }


}