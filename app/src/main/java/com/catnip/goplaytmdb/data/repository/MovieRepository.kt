package com.catnip.goplaytmdb.data.repository

import com.catnip.goplaytmdb.core.base.BaseRepository
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.data.local.CacheKeyConstants
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSource
import com.catnip.goplaytmdb.data.local.entity.CacheEntity
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSource
import com.catnip.goplaytmdb.data.network.model.response.MovieResponse
import com.catnip.goplaytmdb.data.network.model.response.MoviesResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MovieRepository {

    //api
    suspend fun getNowPlayingMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun getUpcomingMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun getPopularMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun getTopRatedMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun getDetailMovie(movieId: Int): Flow<DataResource<MovieResponse>>

    //cache
    suspend fun saveNowPlayingCache(response: MoviesResponse)

    suspend fun saveUpcomingCache(response: MoviesResponse)

    suspend fun savePopularCache(response: MoviesResponse)

    suspend fun saveTopRatedCache(response: MoviesResponse)

}

class MovieRepositoryImpl(
    private val cacheDataSource: CacheDataSource,
    private val apiDataSource: MovieApiDataSource,
    private val gson: Gson
) : MovieRepository, BaseRepository() {
    override suspend fun getNowPlayingMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getNowPlayingMovie(page) })
        }
    }

    override suspend fun getUpcomingMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getUpcomingMovie(page) })
        }
    }

    override suspend fun getPopularMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getPopularMovie(page) })
        }
    }

    override suspend fun getTopRatedMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getTopRatedMovie(page) })
        }
    }

    override suspend fun getDetailMovie(movieId: Int): Flow<DataResource<MovieResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getDetailMovie(movieId) })
        }
    }

    override suspend fun saveNowPlayingCache(response: MoviesResponse) {
        cacheDataSource.addCache(
            CacheEntity(
                CacheKeyConstants.NOW_PLAYING_CACHE_KEY,
                gson.toJson(response)
            )
        )
    }

    override suspend fun saveUpcomingCache(response: MoviesResponse) {

        cacheDataSource.addCache(
            CacheEntity(
                CacheKeyConstants.UPCOMING_CACHE_KEY,
                gson.toJson(response)
            )
        )
    }

    override suspend fun savePopularCache(response: MoviesResponse) {

        cacheDataSource.addCache(
            CacheEntity(
                CacheKeyConstants.POPULAR_CACHE_KEY,
                gson.toJson(response)
            )
        )
    }

    override suspend fun saveTopRatedCache(response: MoviesResponse) {

        cacheDataSource.addCache(
            CacheEntity(
                CacheKeyConstants.TOP_RATED_CACHE_KEY,
                gson.toJson(response)
            )
        )
    }


}