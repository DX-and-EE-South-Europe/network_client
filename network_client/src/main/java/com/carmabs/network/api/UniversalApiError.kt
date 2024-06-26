package com.bimbaylola.network.api

/**
 * Created by Carlos Mateo Benito on 2020-04-03.
 *
 * <p>
 * Copyright (c) 2020 by atSistemas. All rights reserved.
 * </p>
 *
 * @author <a href=“mailto:cmateo.benito@atsistemas.com”>Carlos Mateo Benito</a>
 */
data class UniversalApiError internal constructor(
    val code:Int,
    override val message:String,
    override val cause:Throwable?
) : Throwable()