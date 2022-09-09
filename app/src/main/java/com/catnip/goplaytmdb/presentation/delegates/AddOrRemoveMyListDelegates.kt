package com.catnip.goplaytmdb.presentation.delegates

import androidx.lifecycle.MutableLiveData
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.domain.usecases.AddOrRemoveFavoriteMovieUseCase
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface AddOrRemoveMyListDelegates {
    fun init(scope: CoroutineScope, useCase: AddOrRemoveFavoriteMovieUseCase)
    fun getFavoriteMovieResult(): MutableLiveData<ViewResource<Boolean>>
    fun addOrRemoveWatchlist(movieViewParam: MovieViewParam)
}

class AddOrRemoveMyListDelegatesImpl : AddOrRemoveMyListDelegates {
    private lateinit var scope: CoroutineScope
    private lateinit var useCase: AddOrRemoveFavoriteMovieUseCase
    private val favoriteMovieResult = MutableLiveData<ViewResource<Boolean>>()

    override fun init(scope: CoroutineScope, useCase: AddOrRemoveFavoriteMovieUseCase) {
        this.scope = scope
        this.useCase = useCase
    }

    override fun getFavoriteMovieResult(): MutableLiveData<ViewResource<Boolean>> {
        return favoriteMovieResult
    }

    override fun addOrRemoveWatchlist(movieViewParam: MovieViewParam) {
        scope.launch {
            useCase(AddOrRemoveFavoriteMovieUseCase.Param(movieViewParam)).collect {
                favoriteMovieResult.postValue(it)
            }
        }
    }

}