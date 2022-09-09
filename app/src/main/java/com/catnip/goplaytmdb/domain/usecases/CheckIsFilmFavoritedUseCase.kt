package com.catnip.goplaytmdb.domain.usecases

import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CheckIsFilmFavoritedUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<CheckIsFilmFavoritedUseCase.Param, Boolean>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Boolean>> {
        param?.let {
            return repository.getMovieById(it.movieViewParam.id.toString()).map { result ->
                when (result){
                    is DataResource.Success -> ViewResource.Success(result.payload != null)
                    is DataResource.Error -> ViewResource.Error(result.exception)
                    is DataResource.Loading -> ViewResource.Loading()
                }
            }
        } ?: throw IllegalStateException("Param Required")
    }
    data class Param(val movieViewParam: MovieViewParam)
}