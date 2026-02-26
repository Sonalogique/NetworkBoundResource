package com.sonssetiana.networkboundresouce.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonssetiana.networkboundresouce.data.local.entitys.MovieEntity
import com.sonssetiana.networkboundresouce.data.model.Resource
import com.sonssetiana.networkboundresouce.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val repository: MovieRepository
): ViewModel() {
    private val refreshTrigger = MutableStateFlow(false)

    val movieList: StateFlow<Resource<List<MovieEntity>>> =
        refreshTrigger
            .flatMapLatest { force ->
                repository.getMovieList(force)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                Resource.Loading()
            )

    fun refresh() {
        refreshTrigger.value = true
    }
}