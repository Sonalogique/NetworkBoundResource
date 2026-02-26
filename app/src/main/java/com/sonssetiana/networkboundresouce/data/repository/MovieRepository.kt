package com.sonssetiana.networkboundresouce.data.repository

import com.sonssetiana.networkboundresouce.data.local.dao.MovieDao
import com.sonssetiana.networkboundresouce.data.local.entitys.MovieEntity
import com.sonssetiana.networkboundresouce.data.model.MovieModel
import com.sonssetiana.networkboundresouce.data.model.Resource
import com.sonssetiana.networkboundresouce.data.remote.network.NetworkBoundResource
import com.sonssetiana.networkboundresouce.data.remote.service.MovieServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher

interface MovieRepository {
    fun getMovieList(forceRefresh: Boolean = false): Flow<Resource<List<MovieEntity>>>
}

class MovieRepositoryImpl(
    private val service: MovieServices,
    private val dao: MovieDao
): MovieRepository{
    override fun getMovieList(forceRefresh: Boolean): Flow<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, List<MovieModel>>() {
            override fun query(): Flow<List<MovieEntity>> {
                return dao.selectMovies()
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                if (forceRefresh) return true
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): List<MovieModel> {
                return service.getMoviesList().results
            }

            override suspend fun saveCallResult(items: List<MovieModel>) {
                val mapping = items.map { it.mappingToEntity() }
                dao.upsertMovies(mapping)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

}