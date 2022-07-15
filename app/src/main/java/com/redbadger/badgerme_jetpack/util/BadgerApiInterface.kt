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
        @Header("authorization") token: String,
        @Path("id") id: String
    ): Response<BadgerUser>

    @PATCH("users/{id}")
    suspend fun updateUserInterests(
        @Header("authorization") token: String,
        @Path("id") id: String,
        @Body interestIds: List<String>
    ): Response<Void>

    @GET("interests")
    suspend fun getInterests(
        @Header("authorization") token: String
    ): Response<List<BadgerInterest>>

    @POST("interests")
    suspend fun addInterest(
        @Header("authorization") token: String,
        @Body interest: BadgerInterest
    ): Response<Void>

    @GET("activities")
    suspend fun getActivities(
        @Header("authorization") token: String
    ): Response<List<BadgerEvent>>
}