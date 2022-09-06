package com.catnip.goplaytmdb.data.local.dao

import androidx.room.*
import com.catnip.goplaytmdb.data.local.entity.CacheEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Dao
interface CacheDao {
    @Query("SELECT * FROM cache_table WHERE id == :id LIMIT 1")
    suspend fun getCacheById(id : String?): CacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCache(cache: CacheEntity): Long

    @Delete
    suspend fun removeCache(cache: CacheEntity): Int
}