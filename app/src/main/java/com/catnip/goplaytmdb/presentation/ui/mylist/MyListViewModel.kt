package com.catnip.goplaytmdb.presentation.ui.mylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.domain.usecases.GetFavoritedListMovieUseCase
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MyListViewModel(private val getFavoritedListMovieUseCase: GetFavoritedListMovieUseCase) :
    ViewModel() {

    val favoritedListResult = MutableLiveData<ViewResource<List<MovieViewParam>>>()

    fun getFavoritedList() {
        viewModelScope.launch {
            getFavoritedListMovieUseCase().collect {
               favoritedListResult.postValue(it)
            }
        }
    }
}