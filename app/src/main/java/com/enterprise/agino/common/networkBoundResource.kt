package com.enterprise.agino.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline isEmpty: (ResultType) -> Boolean = { false },
) = flow {
    emit(Resource.Loading())
    val data = query().first()

    if (!isEmpty(data))
        emit(Resource.Success(data))

    val response = if (shouldFetch(data)) {

        val fetched = buildResource { fetch() }
        if (fetched is Resource.Success) {
            saveFetchResult(fetched.value!!)
            query().map { res -> Resource.Success(res) }
        } else {
            query().map { res -> Resource.Error(fetched.message, res) }
        }
    } else {
        query().map { res -> Resource.Success(res) }
    }

    emit(response.first())
}