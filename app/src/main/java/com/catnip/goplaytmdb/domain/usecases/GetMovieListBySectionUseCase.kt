package com.catnip.goplaytmdb.domain.usecases

import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.mapper.MovieResponseMapper
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import com.catnip.goplaytmdb.presentation.model.HomeViewType
import com.catnip.goplaytmdb.utils.mapper.ListMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetMovieListBySectionUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<GetMovieListBySectionUseCase.Param, List<MovieViewParam>>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<List<MovieViewParam>>> {
        param?.let {
            return flow {
                val action = when (param.sectionType) {
                    HomeViewType.SECTION_POPULAR -> repository.fetchPopularMovie(param.page)
                    HomeViewType.SECTION_NOW_PLAYING -> repository.fetchNowPlayingMovie(param.page)
                    HomeViewType.SECTION_TOP_RATED -> repository.fetchTopRatedMovie(param.page)
                    HomeViewType.SECTION_UPCOMING -> repository.fetchUpcomingMovie(param.page)
                    else -> throw IllegalStateException("Unknown Type")
                }
                emitAll(action)
            }.map {
                when (it) {
                    is DataResource.Success -> ViewResource.Success(
                        ListMapper(MovieResponseMapper).toViewParams(it.payload?.results)
                    )
                    is DataResource.Error -> ViewResource.Error(it.exception)
                    is DataResource.Loading -> ViewResource.Loading()
                }
            }.catch {
                emit(ViewResource.Error(it as Exception))
            }
        } ?: throw IllegalStateException("Param Required")
    }

    data class Param(val sectionType: String, val page: Int)
}