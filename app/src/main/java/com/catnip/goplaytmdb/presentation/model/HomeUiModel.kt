package com.catnip.goplaytmdb.presentation.model

import androidx.annotation.StringRes
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

sealed class HomeUiModel(
    val isLoading: Boolean = false,
    val error: Exception? = null
) {
    class HeaderSectionUiModel(
        val movie: MovieViewParam? = null,
        isLoading: Boolean = false,
        error: Exception? = null
    ) : HomeUiModel(isLoading, error)

    class MovieSectionUIModel(
        @StringRes val sectionNameRes: Int,
        val movies: List<MovieViewParam>? = null,
        isLoading: Boolean = false,
        error: Exception? = null
    ) : HomeUiModel(isLoading, error)
}

object HomeViewType {
    const val HEADER = "HEADER"
    const val SECTION_NOW_PLAYING = "SECTION_NOW_PLAYING"
    const val SECTION_UPCOMING = "SECTION_UPCOMING"
    const val SECTION_POPULAR = "SECTION_POPULAR"
    const val SECTION_TOP_RATED = "SECTION_TOP_RATED"
}