package com.enterprise.agino.common

import kotlinx.coroutines.flow.flow

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline fetch: suspend () -> RequestType,
    crossinline mapFetchedValue: (RequestType) -> ResultType,
) = flow {
    emit(Resource.Loading())

    val fetched = buildResource { fetch() }
    val response = if (fetched is Resource.Success) {
        val result = mapFetchedValue(fetched.value!!)
        Resource.Success(result)
    } else {
            val result = mapFetchedValue(fetched.value!!)
        Resource.Error(fetched.message, result)
    }

    emit(response)
}