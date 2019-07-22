package com.ideaware.brightsoccer.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

//Scalable network consumption class
//Reference android architecture with MVVM
abstract class NetworkBound<ResultType> {

    init {
        GlobalScope.launch(Dispatchers.IO) {
            onStartFetching()
            when (val apiResponse = fetchFromNetwork()) {
                is ApiEmptyResponse -> {
                }
                is ApiSuccessResponse -> {
                    processResponse(apiResponse)
                }
                is ApiErrorResponse -> {
                    onFetchFailed(apiResponse)
                }
            }
            onFinishFetching()
        }
    }

    protected abstract suspend fun createCall(): Response<ResultType>

    private suspend fun fetchFromNetwork(): ApiResponse<ResultType> {
        try {
            val response = createCall()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(response.code(), errorMsg ?: "unknown error")
            }
        } catch (exception: Exception) {
            Log.e("Exception", exception.message)
            return ApiErrorResponse(-1, exception.localizedMessage)
        }
    }

    protected abstract fun onFetchFailed(apiErrorResponse: ApiErrorResponse<ResultType>)

    protected abstract fun onStartFetching()

    protected abstract fun onFinishFetching()

    protected abstract fun processResponse(response: ApiSuccessResponse<ResultType>)

}