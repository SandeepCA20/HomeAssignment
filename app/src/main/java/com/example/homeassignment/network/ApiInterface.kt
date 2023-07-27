package com.example.homeassignment.network

import com.example.homeassignment.model.UserInfoData
import com.example.homeassignment.model.UserRepos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/users/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): Response<UserInfoData>

    @GET("/users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): Response<List<UserRepos>>
}