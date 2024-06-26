package com.bimbaylola.network.api

/**
 * Created by Carlos Mateo Benito on 2020-04-01.
 *
 * <p>
 * Copyright (c) 2020 by atSistemas. All rights reserved.
 * </p>
 *
 * @author <a href=“mailto:cmateo.benito@atsistemas.com”>Carlos Mateo Benito</a>
 */
data class UniversalApiResponse<T> internal constructor(
    val statusCode: Int,
    val data: T,
    val headers: Map<String, List<String>>
)