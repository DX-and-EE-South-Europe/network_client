package com.bimbaylola.network.provider.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.HeaderMap
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface HttpAPI {

    companion object {
        const val HTTP_SUCCESS = 200
        const val HTTP_SERVER_ERROR = 500
        const val HTTP_BAD_REQUEST = 400
        const val HTTP_NOT_FOUND = 404
        const val HTTP_NOT_ACCEPTABLE = 404
        const val HTTP_UNAUTHORIZED = 401
        const val HTTP_UNPROCESSABLE_ENTITY = 422
    }

    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap queryParam: Map<String, String>,
        @HeaderMap headers: Map<String, String> = hashMapOf()
    ): Response<Any>

    @GET
    suspend fun download(
        @Url url: String,
        @QueryMap queryParam: Map<String, String>,
        @HeaderMap headers: Map<String, String> = hashMapOf()
    ): ResponseBody

    @GET
    suspend fun getEmptyResponse(
        @Url url: String,
        @QueryMap queryParam: Map<String, String>,
        @HeaderMap headers: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @POST
    suspend fun post(
        @Url url: String,
        @Body postParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @POST
    suspend fun postEmptyResponse(
        @Url url: String,
        @Body postParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @FormUrlEncoded
    @POST
    suspend fun postFormUrlEncoded(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @FormUrlEncoded
    @POST
    suspend fun postFormUrlEncodedEmptyResponse(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @PUT
    suspend fun put(
        @Url url: String,
        @Body putParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @PUT
    suspend fun putEmptyResponse(
        @Url url: String,
        @Body putParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @FormUrlEncoded
    @PUT
    suspend fun putFormUrlEncoded(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @FormUrlEncoded
    @PUT
    suspend fun putFormUrlEncodedEmptyResponse(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @PATCH
    suspend fun patch(
        @Url url: String,
        @Body patchParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @PATCH
    suspend fun patchEmptyResponse(
        @Url url: String,
        @Body patchParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @FormUrlEncoded
    @PATCH
    suspend fun patchFormUrlEncoded(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @FormUrlEncoded
    @PATCH
    suspend fun patchFormUrlEncodedEmptyResponse(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @DELETE
    suspend fun delete(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun delete(
        @Url url: String,
        @Body postParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun deleteEmptyResponse(
        @Url url: String,
        @Body postParamObject: Any,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

    @DELETE
    suspend fun deleteEmptyResponse(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @FormUrlEncoded
    @HTTP(method = "DELETE", hasBody = true)
    suspend fun deleteFormUrlEncoded(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Any>

    @FormUrlEncoded
    @HTTP(method = "DELETE", hasBody = true)
    suspend fun deleteFormUrlEncodedEmptyResponse(
        @Url url: String,
        @FieldMap fieldParam: Map<String, String>,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParam: Map<String, String> = hashMapOf()
    ): Response<Unit>

}