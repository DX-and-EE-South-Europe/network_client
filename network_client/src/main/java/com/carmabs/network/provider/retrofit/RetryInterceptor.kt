package com.bimbaylola.network.provider.retrofit

import com.bimbaylola.network.INT_ZERO
import com.bimbaylola.network.api.UniversalApiError
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class RetryInterceptor(
    private val retries: Int = 1,
    private val retryFunctionCall: suspend (UniversalApiError) -> Boolean
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return retryRequest(chain, response, retries)
    }

    private fun retryRequest(chain: Chain, response: Response, tryNumber: Int): Response {
        return if (response.isSuccessful || tryNumber == INT_ZERO) {
            response
        } else {
            runBlocking{
                if (retryFunctionCall.invoke(UniversalApiError(response.code, response.peekBody(Long.MAX_VALUE).string()?:"",null))) {
                    val newResponse = chain.call().clone().execute()
                    retryRequest(chain, newResponse, (tryNumber - 1).coerceAtLeast(INT_ZERO))
                } else
                    response
            }

        }

    }
}