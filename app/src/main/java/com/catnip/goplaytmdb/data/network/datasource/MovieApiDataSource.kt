package com.catnip.goplaytmdb.data.network.datasource

import com.catnip.goplaytmdb.data.network.MovieApiService
import com.catnip.goplaytmdb.data.network.model.response.MovieResponse
import com.catnip.goplaytmdb.data.network.model.response.MoviesResponse

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MovieApiDataSource {
    suspend fun getNowPlayingMovie(page: Int = 1): MoviesResponse

    suspend fun getUpcomingMovie(page: Int = 1): MoviesResponse

    suspend fun getPopularMovie(page: Int = 1): MoviesResponse

    suspend fun getTopRatedMovie(page: Int = 1): MoviesResponse

    suspend fun getDetailMovie(movieId: Int): MovieResponse
}

class MovieApiDataSourceImpl(private val movieApiService: MovieApiService) : MovieApiDataSource{
    override suspend fun getNowPlayingMovie(page: Int): MoviesResponse {
        return movieApiService.getNowPlayingMovie(page)
    }

    override suspend fun getUpcomingMovie(page: Int): MoviesResponse {
        return movieApiService.getUpcomingMovie(page)
    }

    override suspend fun getPopularMovie(page: Int): MoviesResponse {
        return movieApiService.getPopularMovie(page)
    }

    override suspend fun getTopRatedMovie(page: Int): MoviesResponse {
        return movieApiService.getTopRatedMovie(page)
    }

    override suspend fun getDetailMovie(movieId: Int): MovieResponse {
        return movieApiService.getDetailMovie(movieId)
    }

}