package com.example.plexaarproject.events

sealed class ApiResponse <T> {

    class Empty<T> : ApiResponse<T>()
    class Loading<T> : ApiResponse<T>()
    class Success<T>(var data: T) : ApiResponse<T>()
    class Failure<T>(var error: String) : ApiResponse<T>()
}