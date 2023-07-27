package com.example.homeassignment.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRepos(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("stargazers_count")
    val stargazersCount: String,

    @SerializedName("forks")
    val forks: String
) : Serializable
