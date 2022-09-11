package com.catnip.goplaytmdb.presentation.ui.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.catnip.goplaytmdb.domain.usecases.GetMovieListBySectionUseCase
import com.catnip.goplaytmdb.presentation.ui.movielist.adapter.MoviesBySectionPagingSource
import com.catnip.goplaytmdb.presentation.ui.movielist.adapter.MoviesBySectionPagingSource.Companion.DEFAULT_PAGE_SIZE

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MovieSectionListViewModel(
    private val getMovieListBySectionUseCase: GetMovieListBySectionUseCase
) : ViewModel() {

    fun getMoviesBySection(sectionType: String) = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
        pagingSourceFactory = {
            MoviesBySectionPagingSource(
                getMovieListBySectionUseCase,
                sectionType
            )
        }
    ).flow.cachedIn(viewModelScope)

}