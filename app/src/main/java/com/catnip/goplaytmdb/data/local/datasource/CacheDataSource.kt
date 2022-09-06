package com.catnip.goplaytmdb.data.local.datasource

import com.catnip.goplaytmdb.data.local.dao.CacheDao
import com.catnip.goplaytmdb.data.local.entity.CacheEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CacheDataSource {
    suspend fun getCacheById(id: String): CacheEntity?

    suspend fun addCache(cache: CacheEntity): Long

    suspend fun removeCache(cache: CacheEntity): Int
}

class CacheDataSourceImpl(private val cacheDao: CacheDao) : CacheDataSource {

    override suspend fun getCacheById(id: String): CacheEntity? {
        return cacheDao.getCacheById(id)
    }

    override suspend fun addCache(cache: CacheEntity): Long {
        return cacheDao.addCache(cache)
    }

    override suspend fun removeCache(cache: CacheEntity): Int {
        return cacheDao.removeCache(cache)
    }

}