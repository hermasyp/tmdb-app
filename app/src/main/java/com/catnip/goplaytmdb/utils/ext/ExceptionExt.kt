package com.catnip.goplaytmdb.utils.ext

import android.content.Context
import com.catnip.goplaytmdb.R
import com.catnip.goplaytmdb.core.exception.ApiErrorException
import com.catnip.goplaytmdb.core.exception.EmptyResultException
import com.catnip.goplaytmdb.core.exception.NoInternetConnectionException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

fun Context.getErrorMessage(exception : Exception): String {
    return when (exception) {
        is NoInternetConnectionException -> getString(R.string.message_error_no_internet)
        is EmptyResultException -> getString(R.string.message_error_no_result)
        is ApiErrorException -> exception.message.orEmpty()
        else -> getString(R.string.message_error_unknown)
    }
}