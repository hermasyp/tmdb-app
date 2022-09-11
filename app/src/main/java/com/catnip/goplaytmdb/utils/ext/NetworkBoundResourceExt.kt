package com.catnip.goplaytmdb.utils.ext

import com.catnip.goplaytmdb.core.exception.NoInternetConnectionException
import com.catnip.goplaytmdb.core.wrapper.DataResource
import kotlinx.coroutines.flow.*


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

inline fun <ResultType, RequestType> localFirstNetworkResource(
    crossinline query: suspend () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Flow<RequestType>,
    crossinline saveFetchResult: suspend (Flow<RequestType>) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    val data = query().first()
    val flow = if (shouldFetch(data)) {
        emit(DataResource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { DataResource.Success(it) }
        } catch (throwable: Throwable) {
            if (throwable is NoInternetConnectionException) throw throwable
            query().map { DataResource.Error(Exception(throwable), it) }
        }
    } else {
        query().map { DataResource.Success(it) }
    }
    emitAll(flow)
}