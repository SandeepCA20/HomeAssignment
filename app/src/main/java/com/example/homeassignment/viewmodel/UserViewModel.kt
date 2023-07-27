package com.example.homeassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeassignment.model.UserInfoData
import com.example.homeassignment.model.UserRepos
import com.example.homeassignment.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val userData: LiveData<UserInfoData>
        get() = repository.userInfo

    val userRepos: LiveData<List<UserRepos>>
        get() = repository.userRepos

    val errorMessage: LiveData<String>
        get() = repository.errorMessage
//calling api to get userinfo and user repo data
    suspend fun getUserInfo(userId: String) {
        viewModelScope.launch {
            coroutineScope {
                withContext(Dispatchers.IO) {
                    repository.callApi(userId)
                }
                withContext(Dispatchers.IO) {
                    repository.callUserRepos(userId)
                }
            }
        }
    }
}