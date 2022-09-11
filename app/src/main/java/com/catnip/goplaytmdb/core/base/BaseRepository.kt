package com.catnip.goplaytmdb.core.base

import com.catnip.goplaytmdb.core.exception.ApiErrorException
import com.catnip.goplaytmdb.core.exception.UnexpectedErrorException
import com.catnip.goplaytmdb.core.exception.NoInternetConnectionException
import com.catnip.goplaytmdb.core.wrapper.DataResource
import retrofit2.HttpException
import java.io.IOException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseRepository {

    suspend fun <T> safeNetworkCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> throw NoInternetConnectionException()
                is HttpException -> {
                    DataResource.Error(ApiErrorException(throwable.message(), throwable.code()))
                }
                else -> {
                    DataResource.Error(UnexpectedErrorException())
                }
            }
        }
    }

    suspend fun <T> proceed(coroutine: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            DataResource.Error(exception)
        }
    }
}