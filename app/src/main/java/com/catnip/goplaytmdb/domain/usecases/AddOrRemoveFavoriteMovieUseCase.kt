package com.catnip.goplaytmdb.domain.usecases

import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.mapper.MovieEntityMapper
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AddOrRemoveFavoriteMovieUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<AddOrRemoveFavoriteMovieUseCase.Param, Boolean>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Boolean>> {
        param?.let {
            val movie = MovieEntityMapper.toDataObject(it.movieViewParam)
            val action = if (it.movieViewParam.isFavorited)
                repository.removeMovie(movie)
            else
                repository.addMovie(movie)
            return action.map { result ->
                when (result) {
                    is DataResource.Success -> ViewResource.Success(movie.isFavorited.not())
                    is DataResource.Error -> ViewResource.Error(result.exception)
                    is DataResource.Loading -> ViewResource.Loading()
                }
            }.onStart { emit(ViewResource.Loading()) }.onEach { delay(200) }
        } ?: throw IllegalStateException("Param Required")
    }

    data class Param(val movieViewParam: MovieViewParam)
}