package com.sonssetiana.networkboundresouce.di

import androidx.room.Room
import com.sonssetiana.networkboundresouce.data.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModules = module {
    single {
        Room.databaseBuilder(
                androidContext(),
                LocalDatabase::class.java,
                "movie.db"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<LocalDatabase>().movieDao() }
}