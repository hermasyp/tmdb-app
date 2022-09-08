package com.catnip.goplaytmdb.domain.usecases

import androidx.annotation.StringRes
import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.mapper.MovieMapper
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import com.catnip.goplaytmdb.presentation.model.HomeViewType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetHeaderDataUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<GetHeaderDataUseCase.Param, Pair<String, HomeUiModel>>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Pair<String, HomeUiModel>>> {
        return repository.getMoviesData(HomeViewType.SECTION_NOW_PLAYING).map {
            when (it) {
                is DataResource.Loading -> {
                    ViewResource.Loading(
                        Pair(
                            HomeViewType.HEADER,
                            HomeUiModel.HeaderSectionUiModel(
                                MovieMapper.toViewParam(it.payload?.results?.random()),
                                isLoading = true
                            )
                        )
                    )
                }
                is DataResource.Success -> {
                    ViewResource.Success(
                        Pair(
                            HomeViewType.HEADER,
                            HomeUiModel.HeaderSectionUiModel(
                                MovieMapper.toViewParam(it.payload?.results?.random()),
                            )
                        )
                    )
                }
                is DataResource.Error -> {
                    ViewResource.Error(
                        it.exception,
                        Pair(
                            HomeViewType.HEADER,
                            HomeUiModel.HeaderSectionUiModel(
                                MovieMapper.toViewParam(it.payload?.results?.random()),
                                error = it.exception
                            )
                        )
                    )
                }
            }
        }

    }

    data class Param(val type: String, @StringRes val sectionNameRes: Int)
}