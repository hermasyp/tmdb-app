package com.catnip.goplaytmdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catnip.goplaytmdb.data.local.dao.CacheDao
import com.catnip.goplaytmdb.data.local.entity.CacheEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Database(entities = [CacheEntity::class], version = 1, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun cacheDao() : CacheDao

    companion object {
        private const val DB_NAME = "cache_db"
        fun create(context: Context): CacheDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CacheDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}