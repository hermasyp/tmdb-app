package com.catnip.goplaytmdb.presentation.delegates

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.catnip.goplaytmdb.utils.NetworkStatus
import com.catnip.goplaytmdb.utils.NetworkStatusTracker
import org.koin.java.KoinJavaComponent.inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface NetworkObserverDelegates {
    fun getNetworkStatus(): LiveData<NetworkStatus>
}

class NetworkObserverDelegatesImpl : NetworkObserverDelegates {
    private val networkTracker: NetworkStatusTracker by inject(NetworkStatusTracker::class.java)

    override fun getNetworkStatus(): LiveData<NetworkStatus> {
        return networkTracker.getNetworkStatusFlow().asLiveData()
    }

}