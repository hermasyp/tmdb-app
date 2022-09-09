package com.catnip.goplaytmdb.domain.viewparam

import com.catnip.goplaytmdb.data.Constants


data class MovieViewParam(
    val adult: Boolean,
    val backdropPath: String,
    val genres: List<String>,
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
    val voteCount: Int,
    val isFavorited : Boolean = false
){
    fun getFullPosterPath() : String = Constants.IMAGE_BASE_URL + posterPath
    fun getFullBackdropPath() : String = Constants.IMAGE_BASE_URL + backdropPath
}