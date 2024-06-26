package com.bimbaylola.network.extensions

import com.google.gson.reflect.TypeToken

/**
 * @author <a href=“mailto:cmateo.benito@atsistemas.com”>Carlos Mateo</a>
 */

inline fun <reified T>typeTokenOf() = object : TypeToken<T>(){}