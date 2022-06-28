package com.redbadger.badgerme_jetpack.util

import retrofit2.Response
import retrofit2.http.*

interface BadgerApiInterface {
    @GET("users")
    suspend fun getUserByEmail(
        @Header("authorization") token: String,
        @Query("email") email: String
    ): Response<List<BadgerUser>>

    @GET("users/{id}")
    suspend fun getUserById()

    @POST("users")
    suspend fun addUser (
        @Header("authorization") token: String,
        @Query("email") email: String
    ): Response<String>
}