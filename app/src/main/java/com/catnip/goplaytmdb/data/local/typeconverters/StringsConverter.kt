package com.catnip.goplaytmdb.data.local.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonParseException
import org.koin.java.KoinJavaComponent.inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class StringsConverter {
    private val gson: Gson by inject(Gson::class.java)

    @TypeConverter
    fun fromList(value: List<String>) = gson.toJson(value)

    @TypeConverter
    fun toList(value: String) = try {
        gson.fromJson<List<String>>(value, List::class.java)
    } catch (e: JsonParseException) {
        listOf()
    }
}