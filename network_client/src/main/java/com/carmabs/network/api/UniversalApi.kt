package com.bimbaylola.network.api

import com.bimbaylola.network.STRING_EMPTY
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody

/**
 * Created by Carlos Mateo Benito on 2020-02-25.
 *
 * <p>
 * Copyright (c) 2020 by atSistemas. All rights reserved.
 * </p>
 *
 * @author <a href=“mailto:cmateo.benito@atsistemas.com”>Carlos Mateo Benito</a>
 */
interface UniversalApi {

    suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        vararg param: Param.Query
    ): Result<UniversalApiResponse<T>>


    suspend fun <T> get(
        url: String,
        type: TypeToken<T>,
        vararg param: Param.Query
    ): Result<UniversalApiResponse<T>>

    suspend fun get(url: String, vararg param: Param.Query): Result<UniversalApiResponse<Unit>>

    suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun <T> post(
        url: String,
        type: TypeToken<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun post(
        url: String,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<Unit>>

    suspend fun <T> put(
        url: String,
        clazz: Class<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun <T> put(
        url: String,
        type: TypeToken<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun put(
        url: String,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<Unit>>

    suspend fun <T> patch(
        url: String,
        clazz: Class<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun <T> patch(
        url: String,
        type: TypeToken<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun patch(
        url: String,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<Unit>>

    suspend fun <T> delete(
        url: String,
        clazz: Class<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun <T> delete(
        url: String,
        type: TypeToken<T>,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<T>>

    suspend fun delete(
        url: String,
        vararg param: Param,
        urlEncoded: Boolean = false
    ): Result<UniversalApiResponse<Unit>>

    suspend fun download(
        url: String,
        vararg param: Param.Query
    ): Result<ResponseBody>

    fun setErrorHandler(errorHandler: (code: Int, data: Any?, cause: Throwable) -> Throwable?)

    sealed interface Param {
        data class Header(val key: String = STRING_EMPTY, val value: String = STRING_EMPTY) : Query,
            Body

        sealed interface Query : Param {
            data class KeyValue(val key: String = STRING_EMPTY, val value: String = STRING_EMPTY) :
                Query
        }

        sealed interface Body : Param {
            data class Object(val value: Any = STRING_EMPTY) : Body
            data class KeyValue(val key: String = STRING_EMPTY, val value: String = STRING_EMPTY) :
                Body
        }

        val isHeader: Boolean
            get() = this is Header
    }
}