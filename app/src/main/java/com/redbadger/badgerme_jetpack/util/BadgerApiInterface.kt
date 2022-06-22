package com.redbadger.badgerme_jetpack.util

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface BadgerApiInterface {
    @GET("users")
    suspend fun getUsers()

    @GET("users/{id}")
    suspend fun getUser()

    @POST("users")
    suspend fun addUser (
        @Header("authorization") token: String,
        @Query("email") email: String
    ): Response<Void>
}