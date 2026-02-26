package com.sonssetiana.networkboundresouce.data.remote.network

import com.sonssetiana.networkboundresouce.configs.Configs
import com.sonssetiana.networkboundresouce.data.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow(): Flow<Resource<ResultType>> = flow {

        val dbData = query().firstOrNull()

        if (!shouldFetch(dbData)) {
            emitAll(query().map { Resource.Success(it) })
            return@flow
        }

        emit(Resource.Loading(dbData))

        try {
            val apiResponse = createCall()
            saveCallResult(apiResponse)
            emitAll(query().map { Resource.Success(it) })
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: Configs.MSG_UNKNOWN, dbData))
        }
    }

    protected abstract fun query(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(item: RequestType)
}