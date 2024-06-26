package com.bimbaylola.network.provider.retrofit

import com.bimbaylola.network.api.UniversalApiResponse
import retrofit2.Response

/**
 * Created by Carlos Mateo Benito on 15/7/21
 *
 * <p>
 * Copyright (c) 2021 by AtSistemas. All rights reserved
 * </p>
 *
 * @author <a href="mailto: cmateo.benito@atsistemas.com">Carlos Mateo Benito</a>
 */

internal fun <T> Response<T>.toApiResponse(): UniversalApiResponse<Any> = UniversalApiResponse(
    statusCode = code(),
    data = body() ?: errorBody()?.string() ?: Unit,
    headers = headers().toMultimap()
)