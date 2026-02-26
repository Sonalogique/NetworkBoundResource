package com.sonssetiana.networkboundresouce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sonssetiana.networkboundresouce.data.local.converter.GenreIdsConverter
import com.sonssetiana.networkboundresouce.data.local.dao.MovieDao
import com.sonssetiana.networkboundresouce.data.local.entitys.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreIdsConverter::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}