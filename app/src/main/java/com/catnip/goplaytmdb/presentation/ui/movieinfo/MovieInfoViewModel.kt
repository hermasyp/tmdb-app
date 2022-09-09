package com.catnip.goplaytmdb.presentation.ui.movieinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.domain.usecases.AddOrRemoveFavoriteMovieUseCase
import com.catnip.goplaytmdb.domain.usecases.CheckIsFilmFavoritedUseCase
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.delegates.AddOrRemoveMyListDelegates
import com.catnip.goplaytmdb.presentation.delegates.AddOrRemoveMyListDelegatesImpl
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieInfoViewModel(
    private val checkIsFilmFavoritedUseCase: CheckIsFilmFavoritedUseCase,
    addOrRemoveFavoriteMovieUseCase: AddOrRemoveFavoriteMovieUseCase
) : ViewModel(), AddOrRemoveMyListDelegates by AddOrRemoveMyListDelegatesImpl() {

    val isMovieFavoritedResult = MutableLiveData<ViewResource<Boolean>>()

    init {
        init(viewModelScope, addOrRemoveFavoriteMovieUseCase)
    }

    fun checkIsMovieFavorited(movieViewParam: MovieViewParam) {
        viewModelScope.launch {
            checkIsFilmFavoritedUseCase(CheckIsFilmFavoritedUseCase.Param(movieViewParam)).collect {
                isMovieFavoritedResult.postValue(it)
            }
        }
    }
}