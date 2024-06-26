package com.bimbaylola.network.provider.retrofit

import com.bimbaylola.network.STRING_EMPTY
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.bimbaylola.network.api.UniversalApi
import com.bimbaylola.network.api.UniversalApiError
import com.bimbaylola.network.api.UniversalApiResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUniversalApi(
    gsonConfiguration: (GsonBuilder.() -> Gson)? = null,
    private val retrofitConfiguration: (Retrofit.Builder.() -> Unit)? = null,
    private val debugMode: Boolean = false,
    private val showLogger: Boolean = false,
    private val httpConfiguration: (OkHttpClient.Builder.() -> Unit)? = null
) : UniversalApi {


    private val gson = gsonConfiguration?.invoke(GsonBuilder()) ?: Gson()

    private val httpApi: HttpAPI =
        provideRetrofit(provideOkHttpClient()).create(HttpAPI::class.java)

    private var errorHandler: ((code: Int, exceptionData: Any?, cause: Throwable) -> Throwable?)? =
        null

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = let {
            if (debugMode)
                UnsafeOkHttpClient.getUnsafeOkHttpClient().newBuilder()
            else
                OkHttpClient().newBuilder()
        }
        httpConfiguration?.invoke(builder).let {
            builder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (showLogger) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                }
            ).callTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
        }
        return builder.build()
    }

    private fun provideRetrofit(client: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://www.urlToOverride.com") //This is set only because Retrofit Build ask for it. It will be overriden by this implementation
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                retrofitConfiguration?.invoke(this)
            }
            .build()

    }

    override fun setErrorHandler(errorHandler: (code: Int, exceptionData: Any?, cause: Throwable) -> Throwable?) {
        this.errorHandler = errorHandler
    }

    override suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        vararg param: UniversalApi.Param.Query
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, false,
            Type.GET, *param
        )
        return parseResponse(request, clazz)

    }

    override suspend fun <T> get(
        url: String,
        type: TypeToken<T>,
        vararg param: UniversalApi.Param.Query
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, false,
            Type.GET, *param
        )
        return parseResponse(request, type)
    }

    override suspend fun get(
        url: String,
        vararg param: UniversalApi.Param.Query
    ): Result<UniversalApiResponse<Unit>> {
        val request = obtainEmptyRequest(
            url, false,
            Type.GET, *param
        )
        return parseResponse(request, Unit::class.java)
    }

    override suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.POST, *param
        )
        return parseResponse(request, clazz)
    }

    override suspend fun <T> post(
        url: String,
        type: TypeToken<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.POST, *param
        )
        return parseResponse(request, type)
    }

    override suspend fun post(
        url: String,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<Unit>> {
        val request = obtainEmptyRequest(
            url, urlEncoded,
            Type.POST, *param
        )
        return parseResponse(request, Unit::class.java)
    }

    override suspend fun <T> put(
        url: String,
        clazz: Class<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.PUT, *param
        )
        return parseResponse(request, clazz)
    }

    override suspend fun <T> put(
        url: String,
        type: TypeToken<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.PUT, *param
        )
        return parseResponse(request, type)
    }

    override suspend fun put(
        url: String,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<Unit>> {
        val request = obtainEmptyRequest(
            url, urlEncoded,
            Type.PUT, *param
        )
        return parseResponse(request, Unit::class.java)
    }

    override suspend fun <T> patch(
        url: String, clazz: Class<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.PATCH, *param
        )
        return parseResponse(request, clazz)
    }

    override suspend fun <T> patch(
        url: String,
        type: TypeToken<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.PATCH, *param
        )
        return parseResponse(request, type)
    }

    override suspend fun patch(
        url: String,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<Unit>> {
        val request = obtainEmptyRequest(
            url, urlEncoded,
            Type.PATCH, *param
        )
        return parseResponse(request, Unit::class.java)
    }

    override suspend fun <T> delete(
        url: String,
        clazz: Class<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {

        val request = obtainRequest(
            url, urlEncoded,
            Type.DELETE, *param
        )
        return parseResponse(request, clazz)
    }

    override suspend fun <T> delete(
        url: String,
        type: TypeToken<T>,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<T>> {
        val request = obtainRequest(
            url, urlEncoded,
            Type.DELETE, *param
        )
        return parseResponse(request, type)
    }

    override suspend fun delete(
        url: String,
        vararg param: UniversalApi.Param,
        urlEncoded: Boolean
    ): Result<UniversalApiResponse<Unit>> {
        val request = obtainEmptyRequest(
            url, urlEncoded,
            Type.DELETE, *param
        )
        return parseResponse(request, Unit::class.java)
    }

    override suspend fun download(
        url: String,
        vararg param: UniversalApi.Param.Query
    ): Result<ResponseBody> {
        return try {
            Result.success(httpApi.download(url, generateQueryMap(param), getHeaderMap(param)))
        } catch (e: Throwable) {
            Result.failure(
                UniversalApiError(
                    HttpAPI.HTTP_SERVER_ERROR,
                    e.message ?: e.localizedMessage,
                    e
                )
            )
        }
    }

    private suspend fun obtainRequest(
        url: String,
        urlEncoded: Boolean,
        type: Type,
        vararg params: UniversalApi.Param
    ): Result<UniversalApiResponse<Any>> {
        try {
            val mapQueryParams = generateQueryMap(params)
            val mapBodyParams = generateBodyMap(params)
            val mapHeader = generateHeaderMap(params)
            val response = when (type) {
                Type.GET -> {
                    httpApi.get(url = url, queryParam = mapQueryParams, headers = mapHeader)
                }

                Type.POST -> {
                    if (urlEncoded) {
                        httpApi.postFormUrlEncoded(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.post(
                            url = url,
                            postParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    }
                }

                Type.PUT -> {
                    if (urlEncoded) {
                        httpApi.putFormUrlEncoded(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.put(
                            url = url,
                            putParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    }
                }

                Type.PATCH -> {
                    if (urlEncoded) {
                        httpApi.patchFormUrlEncoded(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.patch(
                            url = url,
                            patchParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )

                    }
                }

                Type.DELETE -> {
                    if (urlEncoded) {
                        httpApi.deleteFormUrlEncoded(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        if (params.isEmpty()) {
                            httpApi.delete(url, headers = mapHeader)
                        } else {
                            val data = getDataObject(*params)
                            httpApi.delete(
                                url = url,
                                postParamObject = data,
                                headers = mapHeader,
                                queryParam = mapQueryParams
                            )
                        }

                    }
                }
            }

            return Result.success(response.toApiResponse())
        } catch (e: Throwable) {
            return Result.failure(
                UniversalApiError(
                    HttpAPI.HTTP_SERVER_ERROR,
                    e.message ?: e.stackTraceToString(),
                    e
                )
            )
        }
    }

    private suspend fun obtainEmptyRequest(
        url: String,
        urlEncoded: Boolean,
        type: Type,
        vararg params: UniversalApi.Param
    ): Result<UniversalApiResponse<Any>> {
        return try {
            val mapQueryParams = generateQueryMap(params)
            val mapHeader = generateHeaderMap(params)
            val mapBodyParams = generateBodyMap(params)
            val response = when (type) {
                Type.GET -> {
                    httpApi.getEmptyResponse(
                        url = url,
                        queryParam = mapQueryParams,
                        headers = mapHeader
                    )
                }

                Type.POST -> {
                    if (urlEncoded) {
                        httpApi.postFormUrlEncodedEmptyResponse(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.postEmptyResponse(
                            url = url,
                            postParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    }
                }

                Type.PUT -> {
                    if (urlEncoded) {
                        httpApi.putFormUrlEncodedEmptyResponse(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.putEmptyResponse(
                            url = url,
                            putParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    }
                }

                Type.PATCH -> {
                    if (urlEncoded) {
                        httpApi.patchFormUrlEncodedEmptyResponse(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        val data = getDataObject(*params)
                        httpApi.patchEmptyResponse(
                            url = url,
                            patchParamObject = data,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )

                    }
                }

                Type.DELETE -> {
                    if (urlEncoded) {
                        httpApi.deleteFormUrlEncodedEmptyResponse(
                            url = url,
                            fieldParam = mapBodyParams,
                            headers = mapHeader,
                            queryParam = mapQueryParams
                        )
                    } else {
                        if (params.isEmpty()) {
                            httpApi.deleteEmptyResponse(url = url, headers = mapHeader)
                        } else {
                            val data = getDataObject(*params)
                            httpApi.deleteEmptyResponse(
                                url = url,
                                postParamObject = data,
                                headers = mapHeader,
                                queryParam = mapQueryParams
                            )
                        }
                    }
                }
            }

            return Result.success(response.toApiResponse())
        } catch (e: Throwable) {
            Result.failure(
                UniversalApiError(
                    HttpAPI.HTTP_SERVER_ERROR,
                    e.message ?: e.stackTraceToString(),
                    e
                )
            )
        }
    }

    private fun getDataObject(vararg params: UniversalApi.Param): Any {
        val bodyParams = params.filter { !it.isHeader && it is UniversalApi.Param.Body }
        val data: Any = if (bodyParams.size == 1) {
            val param = bodyParams[0]
            if (param is UniversalApi.Param.Body.Object)
                param.value
            else {
                val keyValue = param as UniversalApi.Param.Body.KeyValue
                HashMap<String, String>().apply {
                    put(keyValue.key, keyValue.value)
                }
            }

        } else {
            val map = HashMap<String, String>()
            bodyParams.forEach {
                if (it is UniversalApi.Param.Body.Object)
                    throw IllegalStateException(
                        "You can't put a Param.Body.Object with Param.KeyValue," +
                                " put only one Param.Body.Object to represent the data, or put the data with " +
                                "the necessary Param.KeyValue, but no mix them."
                    )
                else {
                    val param = it as UniversalApi.Param.Body.KeyValue
                    map[param.key] = param.value
                }
            }
            map
        }
        return data
    }

    private fun getHeaderMap(headers: Array<out UniversalApi.Param>): MutableMap<String, String> {
        return if (headers.isEmpty())
            mutableMapOf()
        else {
            val map: MutableMap<String, String> = mutableMapOf()
            headers.forEach {
                if (it is UniversalApi.Param.Header)
                    map[it.key] = it.value
            }
            if (map.isEmpty()) mutableMapOf() else map
        }
    }

    private fun <T> parseResponse(
        response: Result<UniversalApiResponse<Any>>,
        clazz: Class<T>
    ): Result<UniversalApiResponse<T>> = response.getOrNull()?.let {
        try {
            val json = gson.toJson(it.data)
            val data = gson.fromJson(json, clazz) as T
            val apiResponse = UniversalApiResponse(
                it.statusCode,
                data,
                it.headers
            )
            Result.success(apiResponse)
        } catch (e: Throwable) {
            Result.failure(
                errorHandler?.invoke(it.statusCode, it.data,e)
                    ?: UniversalApiError(it.statusCode, it.data.toString(), e)
            )
        }
    } ?: Result.failure(response.exceptionOrNull()!!)

    private fun <T> parseResponse(
        response: Result<UniversalApiResponse<Any>>,
        type: TypeToken<T>
    ): Result<UniversalApiResponse<T>> = response.getOrNull()?.let {
        try {
            val json = gson.toJson(it.data)
            val data = gson.fromJson(json, type.type) as T
            val apiResponse = UniversalApiResponse(
                it.statusCode,
                data,
                it.headers
            )
            Result.success(apiResponse)
        } catch (e: Throwable) {
            Result.failure(
                errorHandler?.invoke(it.statusCode, it.data,e)
                    ?: UniversalApiError(it.statusCode, e.message ?: STRING_EMPTY, e)
            )
        }
    } ?: Result.failure(response.exceptionOrNull()!!)

    private fun generateQueryMap(param: Array<out UniversalApi.Param>): Map<String, String> {
        return generateMap(param) { it is UniversalApi.Param.Query && !it.isHeader }
    }

    private fun generateHeaderMap(param: Array<out UniversalApi.Param>): Map<String, String> {
        return generateMap(param) { it.isHeader }
    }

    private fun generateBodyMap(param: Array<out UniversalApi.Param>): Map<String, String> {
        return generateMap(param) { it is UniversalApi.Param.Body && !it.isHeader }
    }

    private fun generateMap(
        param: Array<out UniversalApi.Param>,
        filter: ((UniversalApi.Param) -> Boolean)? = null
    ): Map<String, String> {
        val map = HashMap<String, String>()
        val filteredParams = filter?.let { param.filter(it).toTypedArray() } ?: param
        filteredParams.forEach {
            when (val parameter = it) {
                is UniversalApi.Param.Query.KeyValue -> map[parameter.key] = parameter.value
                is UniversalApi.Param.Body.KeyValue -> map[parameter.key] = parameter.value
                is UniversalApi.Param.Header -> map[parameter.key] = parameter.value
                is UniversalApi.Param.Body.Object -> {
                    fillMapWithValueFields(map, parameter.value)
                }
            }
        }
        return map
    }

    private fun fillMapWithValueFields(
        map: MutableMap<String, String>,
        value: Any
    ) {
        value::class.java.declaredFields.forEach { field ->
            field.isAccessible = true
            field.get(value)?.also { value ->
                val serializedName = field.getAnnotation(SerializedName::class.java)
                val valueSerialized = serializedName?.let { serial ->
                    serial::value.get()
                }
                map[valueSerialized ?: field.name] = value.toString()
            }
        }
    }

    private enum class Type {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE
    }
}