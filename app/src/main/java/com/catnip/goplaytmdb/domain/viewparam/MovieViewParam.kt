package com.catnip.goplaytmdb.domain.viewparam


data class MovieViewParam(
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<GenreViewParam>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)