package com.sonssetiana.networkboundresouce.data.remote.network

data class ApiResponse<out T: Any>(
    val page: Int,
    val results: T,
    val total_pages: Int,
    val total_results: Int
)
