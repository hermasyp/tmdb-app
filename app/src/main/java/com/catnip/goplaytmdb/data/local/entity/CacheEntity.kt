package com.catnip.goplaytmdb.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Entity(tableName = "cache_table")
data class CacheEntity(
    @PrimaryKey
    var id : String,
    @ColumnInfo(name = "cache")
    var cache : String,
)
