package com.sonssetiana.networkboundresouce.data.remote.service

import com.sonssetiana.networkboundresouce.data.model.MovieModel
import com.sonssetiana.networkboundresouce.data.remote.network.ApiResponse
import retrofit2.http.GET

interface MovieServices {
    @GET("movie/popular")
    suspend fun getMoviesList(): ApiResponse<List<MovieModel>>
}