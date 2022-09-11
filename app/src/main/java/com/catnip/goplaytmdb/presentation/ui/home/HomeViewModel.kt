package com.catnip.goplaytmdb.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.catnip.goplaytmdb.presentation.delegates.NetworkObserverDelegates
import com.catnip.goplaytmdb.presentation.delegates.NetworkObserverDelegatesImpl

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeViewModel : ViewModel(), NetworkObserverDelegates by NetworkObserverDelegatesImpl()
