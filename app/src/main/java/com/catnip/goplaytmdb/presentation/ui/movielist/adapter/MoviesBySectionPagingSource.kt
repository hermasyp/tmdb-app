package com.catnip.goplaytmdb.presentation.ui.movielist.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.catnip.goplaytmdb.core.exception.UnexpectedErrorException
import com.catnip.goplaytmdb.core.wrapper.ViewResource
import com.catnip.goplaytmdb.domain.usecases.GetMovieListBySectionUseCase
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MoviesBySectionPagingSource(
    private val getMovieListBySectionUseCase: GetMovieListBySectionUseCase,
    private val sectionType: String
) : PagingSource<Int, MovieViewParam>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieViewParam> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return getMovieListBySectionUseCase(GetMovieListBySectionUseCase.Param(sectionType,page))
            .map {
                when (it) {
                    is ViewResource.Success -> {
                        LoadResult.Page(
                            it.payload?.toList().orEmpty(),
                            prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                            nextKey = if (it.payload?.isEmpty() == true) null else page + 1
                        )
                    }
                    is ViewResource.Error -> {
                        it.exception?.let { e ->
                            LoadResult.Error(e)
                        } ?: LoadResult.Error(UnexpectedErrorException())
                    }
                    else -> {
                        LoadResult.Error(IllegalStateException())
                    }
                }
            }.first()
    }

    override fun getRefreshKey(state: PagingState<Int, MovieViewParam>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 5
    }
}