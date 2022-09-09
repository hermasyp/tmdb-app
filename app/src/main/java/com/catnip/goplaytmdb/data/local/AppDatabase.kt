package com.catnip.goplaytmdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catnip.goplaytmdb.data.local.dao.CacheDao
import com.catnip.goplaytmdb.data.local.dao.MovieDao
import com.catnip.goplaytmdb.data.local.entity.CacheEntity
import com.catnip.goplaytmdb.data.local.entity.MovieEntity

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Database(entities = [CacheEntity::class,MovieEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cacheDao() : CacheDao
    abstract fun MovieDao() : MovieDao

    companion object {
        private const val DB_NAME = "goplay_tmdb_db"
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}