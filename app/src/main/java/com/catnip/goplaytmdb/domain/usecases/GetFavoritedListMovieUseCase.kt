package com.catnip.goplaytmdb.domain.usecases

import com.catnip.goplaytmdb.utils.mapper.ListMapper
import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.mapper.MovieEntityMapper
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetFavoritedListMovieUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, List<MovieViewParam>>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<List<MovieViewParam>>> {
        return repository.getUserMovies().map {
            when (it) {
                is DataResource.Success -> ViewResource.Success(
                    ListMapper(MovieEntityMapper).toViewParams(it.payload)
                )
                is DataResource.Error -> ViewResource.Error(it.exception)
                is DataResource.Loading -> ViewResource.Loading()
            }
        }
    }
}