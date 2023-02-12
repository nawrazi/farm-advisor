package com.enterprise.agino.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

@ProvidedTypeConverter
class ListToJsonConverter @Inject constructor(private val gson: Gson) {
    private val listType: Type = object : TypeToken<List<String?>?>() {}.type

    @TypeConverter
    fun jsonToStringList(json: String?): List<String> {
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun stringListToJson(target: List<String>?): String {
        return gson.toJson(target, listType)
    }
}