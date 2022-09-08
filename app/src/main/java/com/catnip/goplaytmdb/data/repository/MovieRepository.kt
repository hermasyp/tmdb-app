package com.catnip.goplaytmdb.data.repository

import android.util.Log
import com.catnip.goplaytmdb.core.base.BaseRepository
import com.catnip.goplaytmdb.core.wrapper.DataResource
import com.catnip.goplaytmdb.data.local.CacheKeyConstants
import com.catnip.goplaytmdb.data.local.datasource.CacheDataSource
import com.catnip.goplaytmdb.data.local.entity.CacheEntity
import com.catnip.goplaytmdb.data.network.datasource.MovieApiDataSource
import com.catnip.goplaytmdb.data.network.model.response.MovieResponse
import com.catnip.goplaytmdb.data.network.model.response.MoviesResponse
import com.catnip.goplaytmdb.presentation.model.HomeViewType
import com.catnip.goplaytmdb.utils.ext.localFirstNetworkResource
import com.catnip.goplaytmdb.utils.ext.suspendSubscribe
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MovieRepository {

    //api
    suspend fun fetchNowPlayingMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun fetchUpcomingMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun fetchPopularMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun fetchTopRatedMovie(page: Int = 1): Flow<DataResource<MoviesResponse>>

    suspend fun fetchDetailMovie(movieId: Int): Flow<DataResource<MovieResponse>>

    //cache
    suspend fun saveCache(key: String, response: MoviesResponse)

    suspend fun getCache(
        key: String
    ): Flow<DataResource<MoviesResponse>>

    suspend fun getMoviesData(viewType : String): Flow<DataResource<MoviesResponse>>
}

class MovieRepositoryImpl(
    private val cacheDataSource: CacheDataSource,
    private val apiDataSource: MovieApiDataSource,
    private val gson: Gson
) : MovieRepository, BaseRepository() {

    override suspend fun fetchNowPlayingMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getNowPlayingMovie(page) })
        }
    }

    override suspend fun fetchUpcomingMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getUpcomingMovie(page) })
        }
    }

    override suspend fun fetchPopularMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getPopularMovie(page) })
        }
    }

    override suspend fun fetchTopRatedMovie(page: Int): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getTopRatedMovie(page) })
        }
    }

    override suspend fun fetchDetailMovie(movieId: Int): Flow<DataResource<MovieResponse>> {
        return flow {
            emit(safeNetworkCall { apiDataSource.getDetailMovie(movieId) })
        }
    }

    override suspend fun saveCache(key: String, response: MoviesResponse) {
        cacheDataSource.addCache(
            CacheEntity(
                key,
                gson.toJson(response)
            )
        )
    }

    override suspend fun getCache(
        key: String
    ): Flow<DataResource<MoviesResponse>> {
        return flow {
            emit(proceed {
                gson.fromJson(
                    cacheDataSource.getCacheById(key)?.cache,
                    MoviesResponse::class.java
                )
            })
        }
    }

    override suspend fun getMoviesData(viewType : String): Flow<DataResource<MoviesResponse>> {
        return localFirstNetworkResource(
            query = {
                getCache(getCacheKey(viewType)).map {
                    when (it) {
                        is DataResource.Success -> {
                            it.payload ?: MoviesResponse(0, listOf(), 0, 0)
                        }
                        else -> {
                            Log.d("TAG", "invoke: error null movies")
                            MoviesResponse(0, listOf(), 0, 0)
                        }
                    }
                }
            },
            fetch = { fetchMovies(viewType) },
            saveFetchResult = {
                it.collect { data ->
                    data.suspendSubscribe(
                        doOnSuccess = { result ->
                            result.payload?.let { response -> saveCache(getCacheKey(viewType),response) }
                        },
                        doOnError = { error ->
                            Log.d("TAG", "invoke: error $error")

                            error.exception?.let { e ->
                                throw e
                            }
                        }
                    )
                }
            }
        )
    }

    private fun getCacheKey(viewType: String) : String{
        return when(viewType){
            HomeViewType.SECTION_NOW_PLAYING -> CacheKeyConstants.NOW_PLAYING_CACHE_KEY
            HomeViewType.SECTION_POPULAR -> CacheKeyConstants.POPULAR_CACHE_KEY
            HomeViewType.SECTION_TOP_RATED -> CacheKeyConstants.TOP_RATED_CACHE_KEY
            HomeViewType.SECTION_UPCOMING -> CacheKeyConstants.UPCOMING_CACHE_KEY
            else -> {""}
        }
    }
    private suspend fun fetchMovies(viewType: String) : Flow<DataResource<MoviesResponse>>{
        return when(viewType){
            HomeViewType.SECTION_NOW_PLAYING -> fetchNowPlayingMovie()
            HomeViewType.SECTION_POPULAR -> fetchPopularMovie()
            HomeViewType.SECTION_TOP_RATED -> fetchTopRatedMovie()
            HomeViewType.SECTION_UPCOMING -> fetchUpcomingMovie()
            else -> {fetchNowPlayingMovie()}
        }
    }

}