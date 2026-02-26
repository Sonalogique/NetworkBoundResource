package com.sonssetiana.networkboundresouce.di

import com.sonssetiana.networkboundresouce.data.repository.MovieRepository
import com.sonssetiana.networkboundresouce.data.repository.MovieRepositoryImpl
import com.sonssetiana.networkboundresouce.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val domainModules = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

    viewModel { MainViewModel(get()) }
}