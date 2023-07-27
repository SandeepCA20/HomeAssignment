package com.example.homeassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.homeassignment.model.UserInfoData
import com.example.homeassignment.model.UserRepos
import com.example.homeassignment.network.ApiInterface
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiInterface: ApiInterface) {

    private val userLiveData = MutableLiveData<UserInfoData>()
    val userInfo: LiveData<UserInfoData>
        get() = userLiveData

    private val userRepoLiveData = MutableLiveData<List<UserRepos>>()
    val userRepos: LiveData<List<UserRepos>>
        get() = userRepoLiveData

    private val errorMutableMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = errorMutableMessage

    suspend fun callApi(userId: String) {
        val result = apiInterface.getUserInfo(userId)
        if (result.isSuccessful && result.body() != null) {
            userLiveData.postValue(result.body())
        } else {
            val error = result.errorBody()?.source()?.buffer?.snapshot()?.utf8()
            errorMutableMessage.postValue(error)
        }
    }

    suspend fun callUserRepos(userId: String) {
        val userRepoData = apiInterface.getUserRepos(userId)
        if (userRepoData.isSuccessful && userRepoData.body() != null) {
            userRepoLiveData.postValue(userRepoData.body())
        }
    }
}