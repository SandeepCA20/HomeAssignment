package com.example.homeassignment.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfoData(
    @SerializedName("name")
    val name: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
) : Serializable
