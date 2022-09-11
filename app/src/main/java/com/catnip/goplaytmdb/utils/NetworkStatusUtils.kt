package com.catnip.goplaytmdb.utils

import android.content.Context
import android.net.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class NetworkStatusTracker(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getNetworkStatusFlow() : Flow<NetworkStatus>{
        return callbackFlow {
            try {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                // Set the initial value for the live-data
                trySend(if(networkInfo != null
                    && networkInfo.isConnected) NetworkStatus.Available else NetworkStatus.Unavailable)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(NetworkStatus.Unavailable)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.Available)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.Unavailable)
                }
            }



            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(request, networkStatusCallback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(networkStatusCallback)
            }
        }.distinctUntilChanged()
    }

}

enum class NetworkStatus {
    Unavailable, Available
}

inline fun <Result> Flow<NetworkStatus>.observe(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}
