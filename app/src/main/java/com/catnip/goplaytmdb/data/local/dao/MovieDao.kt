package com.catnip.goplaytmdb.data.local.dao

import androidx.room.*
import com.catnip.goplaytmdb.data.local.entity.CacheEntity
import com.catnip.goplaytmdb.data.local.entity.MovieEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Dao
interface MovieDao {
    @Query("SELECT * FROM user_movie_list WHERE id == :id LIMIT 1")
    suspend fun getMovieById(id : String): MovieEntity?

    @Query("SELECT * FROM USER_MOVIE_LIST")
    suspend fun getUserMovies() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movieEntity: MovieEntity): Long

    @Delete
    suspend fun removeMovie(movieEntity: MovieEntity): Int
}