package com.sonssetiana.networkboundresouce.data.remote.network

import android.system.ErrnoException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sonssetiana.networkboundresouce.configs.Configs
import com.sonssetiana.networkboundresouce.data.model.ApiError
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

fun Exception.parseErrorMessage(): ApiError {
    return when (this) {
        is UnknownHostException -> {
            ApiError(0, Configs.MSG_HOST_NOT_FOUND)
        }
        is ConnectException -> {
            ApiError(0, Configs.MSG_FAILED_CONNECT_TO_SERVER)
        }
        is ErrnoException -> {
            ApiError(0, Configs.MSG_TIME_OUT)
        }
        is IOException -> {
            var message = this.message ?: Configs.MSG_UNKNOWN
            val isTimeOut = message.equals("timeout", true)
            val code = if (isTimeOut) 0 else 99
            val result = if (isTimeOut) {
                "Timeout"
            } else message
            ApiError(code, result)
        }
        is HttpException -> {
            try {
                val gson = Gson()
                val type = object : TypeToken<ApiError>() {}.type
                val response = this.response()
                val errorResponse: ApiError = gson.fromJson(response?.errorBody()?.charStream(), type)
                if (errorResponse.code == null) errorResponse.copy(code = response?.code()) else errorResponse
            } catch (e: Exception) {
                ApiError(99, this.message ?: Configs.MSG_UNKNOWN)
            }
        }
        is IllegalArgumentException -> {
            ApiError(99, this.message ?: Configs.MSG_UNKNOWN)
        }
        else -> {
            ApiError(99, this.localizedMessage ?: Configs.MSG_UNKNOWN)
        }
    }
}