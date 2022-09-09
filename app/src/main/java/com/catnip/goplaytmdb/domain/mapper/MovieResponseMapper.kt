package com.catnip.goplaytmdb.domain.mapper

import com.catnip.ajaibpretest.utils.mapper.ViewParamMapper
import com.catnip.goplaytmdb.data.network.model.response.MovieResponse
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object MovieResponseMapper : ViewParamMapper<MovieResponse, MovieViewParam> {
    override fun toViewParam(dataObject: MovieResponse?) = MovieViewParam(
        adult = dataObject?.adult ?: false,
        backdropPath = dataObject?.backdropPath.orEmpty(),
        genres = dataObject?.genres?.map { it.name.orEmpty() } ?: listOf(),
        id = dataObject?.id ?: -1,
        originalLanguage = dataObject?.originalLanguage.orEmpty(),
        originalTitle = dataObject?.originalTitle.orEmpty(),
        overview = dataObject?.overview.orEmpty(),
        popularity = dataObject?.popularity ?: -1.0,
        releaseDate = dataObject?.releaseDate.orEmpty(),
        runtime = dataObject?.runtime ?: -1,
        status = dataObject?.status.orEmpty(),
        title = dataObject?.title.orEmpty(),
        voteAverage = dataObject?.voteAverage ?: -1.0,
        voteCount = dataObject?.voteCount ?: -1,
        posterPath = dataObject?.posterPath.orEmpty()
    )
}