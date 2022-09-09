package com.catnip.goplaytmdb.domain.usecases

import androidx.annotation.StringRes
import com.catnip.ajaibpretest.utils.mapper.ListMapper
import com.catnip.goplaytmdb.core.base.BaseUseCase
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.data.repository.MovieRepository
import com.catnip.goplaytmdb.domain.mapper.MovieResponseMapper
import com.catnip.goplaytmdb.presentation.model.HomeUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class GetSectionDataUseCase(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<GetSectionDataUseCase.Param, Pair<String, HomeUiModel>>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Pair<String, HomeUiModel>>> {
        return param?.let { paramData ->
            repository.getMoviesData(paramData.type).map {
                when (it) {
                    is DataResource.Loading -> {
                        ViewResource.Loading(
                            Pair(
                                paramData.type,
                                HomeUiModel.MovieSectionUIModel(
                                    paramData.sectionNameRes,
                                    ListMapper(MovieResponseMapper).toViewParams(it.payload?.results),
                                    isLoading = true
                                )
                            )
                        )
                    }
                    is DataResource.Success -> {
                        ViewResource.Success(
                            Pair(
                                paramData.type,
                                HomeUiModel.MovieSectionUIModel(
                                    paramData.sectionNameRes,
                                    ListMapper(MovieResponseMapper).toViewParams(it.payload?.results)
                                )
                            )
                        )
                    }
                    is DataResource.Error -> {
                        ViewResource.Error(
                            it.exception,
                            Pair(
                                paramData.type,
                                HomeUiModel.MovieSectionUIModel(
                                    paramData.sectionNameRes,
                                    ListMapper(MovieResponseMapper).toViewParams(it.payload?.results),
                                    error = it.exception
                                )
                            )
                        )
                    }
                }
            }
        } ?: throw IllegalStateException("Requires Param")
    }

    data class Param(val type : String, @StringRes val sectionNameRes: Int )
}