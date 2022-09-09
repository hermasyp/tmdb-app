package com.catnip.goplaytmdb.domain.mapper

import com.catnip.ajaibpretest.utils.mapper.DataMapper
import com.catnip.goplaytmdb.data.local.entity.MovieEntity
import com.catnip.goplaytmdb.domain.viewparam.MovieViewParam

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object MovieEntityMapper : DataMapper<MovieEntity, MovieViewParam> {
    override fun toViewParam(dataObject: MovieEntity?) =
        MovieViewParam(
            adult = dataObject?.adult ?: false,
            backdropPath = dataObject?.backdropPath.orEmpty(),
            genres = dataObject?.genres.orEmpty(),
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

    override fun toDataObject(viewParam: MovieViewParam?): MovieEntity =
        MovieEntity(
            adult = viewParam?.adult ?: false,
            backdropPath = viewParam?.backdropPath.orEmpty(),
            genres = viewParam?.genres.orEmpty(),
            id = viewParam?.id ?: -1,
            originalLanguage = viewParam?.originalLanguage.orEmpty(),
            originalTitle = viewParam?.originalTitle.orEmpty(),
            overview = viewParam?.overview.orEmpty(),
            popularity = viewParam?.popularity ?: -1.0,
            releaseDate = viewParam?.releaseDate.orEmpty(),
            runtime = viewParam?.runtime ?: -1,
            status = viewParam?.status.orEmpty(),
            title = viewParam?.title.orEmpty(),
            voteAverage = viewParam?.voteAverage ?: -1.0,
            voteCount = viewParam?.voteCount ?: -1,
            posterPath = viewParam?.posterPath.orEmpty()
        )
}