package com.catnip.goplaytmdb.data.local.datasource

import com.catnip.goplaytmdb.data.local.dao.MovieDao
import com.catnip.goplaytmdb.data.local.entity.MovieEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface MovieLocalDataSource {
    suspend fun getMovieById(id : String): MovieEntity?

    suspend fun addMovie(movieEntity: MovieEntity): Long

    suspend fun removeMovie(movieEntity: MovieEntity): Int

    suspend fun getUserMovies() : List<MovieEntity>

}

class MovieLocalDataSourceImpl(private val movieDao: MovieDao) : MovieLocalDataSource{
    override suspend fun getMovieById(id: String): MovieEntity? {
        return movieDao.getMovieById(id)
    }

    override suspend fun addMovie(movieEntity: MovieEntity): Long {
        return movieDao.addMovie(movieEntity)
    }

    override suspend fun removeMovie(movieEntity: MovieEntity): Int {
        return movieDao.removeMovie(movieEntity)
    }

    override suspend fun getUserMovies(): List<MovieEntity> {
        return movieDao.getUserMovies()
    }

}