package com.sonssetiana.networkboundresouce.data.local.converter

import androidx.room.TypeConverter

class GenreIdsConverter {

    @TypeConverter
    fun fromGenreIds(value: List<Int>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toGenreIds(value: String): List<Int> {
        if (value.isEmpty()) return emptyList()
        return value.split(",").map { it.toInt() }
    }
}