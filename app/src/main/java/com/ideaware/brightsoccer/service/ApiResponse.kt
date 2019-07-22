package com.ideaware.brightsoccer.service

@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T>

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorCode: Int = -1, val errorMessage: String) : ApiResponse<T>()