package com.enterprise.agino.common

import android.util.Log
import com.enterprise.agino.common.exceptions.APIException
import com.enterprise.agino.common.exceptions.NetworkException
import com.enterprise.agino.common.exceptions.ServerError

sealed class Resource<T>(val value: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(null, message) {
        val errMsg: String
            get() = super.message ?: "Unknown Error"
    }

    class Loading<T> : Resource<T>()
}

/**
 * Build a resource object from the given block without explicitly writing a try catch block.
 */
inline fun <R> buildResource(block: () -> R): Resource<R> {
    return try {
        Resource.Success(block())
    } catch (e: APIException) {
        Resource.Error(e.message)
    } catch (e: ServerError) {
        Resource.Error(e.message)
    } catch (e: NetworkException) {
        Resource.Error(e.message)
    } catch (e: java.io.IOException) {
        Resource.Error("Network error")
    } catch (e: Exception) {
        Log.e("AlenResourceBuilder", "Unknown error", e)
        Resource.Error("Unknown Error")
    }
}
