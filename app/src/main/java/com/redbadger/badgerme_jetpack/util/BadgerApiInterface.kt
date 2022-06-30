package com.redbadger.badgerme_jetpack.util

import retrofit2.Response
import retrofit2.http.*

interface BadgerApiInterface {
    @GET("users")
    suspend fun getUserByEmail(
        @Header("authorization") token: String,
        @Query("email") email: String
    ): Response<List<BadgerUser>>

    @POST("users")
    suspend fun addUser (
        @Header("authorization") token: String,
        @Query("email") email: String
    ): Response<BadgerUser>

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: String
    ): Response<BadgerUser>

    @PATCH("users/{id}")
    suspend fun updateUserInterests(
        @Path("id") id: String,
        @Body interestIds: List<String>
    ): Response<Void>

    @GET("interests")
    suspend fun getInterests(): Response<List<BadgerInterest>>
}