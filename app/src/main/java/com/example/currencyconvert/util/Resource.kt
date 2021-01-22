package com.example.currencyconvert.util

//used to wrap around the repository for handling errors and responses from api
sealed class Resource<T>(val data: T?, val responseMessage: String?) {

    //for successful response with data from api
    class Success<T>(data: T): Resource<T>(data, null)

    //for failure in response from api
    class Error<T>(responseMessage: String): Resource<T>(null, responseMessage)
}