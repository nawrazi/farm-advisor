package com.enterprise.agino.utils

import com.enterprise.agino.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.transform

fun <T> Flow<Resource<T>>.reEmit(flowCollector: FlowCollector<Resource<Unit>>): Flow<Resource<Unit>> {
    return this.transform { resource ->
        when (resource) {
            is Resource.Loading -> flowCollector.emit(Resource.Loading())
            is Resource.Success -> flowCollector.emit(Resource.Success(data = Unit))
            is Resource.Error -> flowCollector.emit(
                Resource.Error(
                    resource.message ?: "Unknown error"
                )
            )
        }
    }
}