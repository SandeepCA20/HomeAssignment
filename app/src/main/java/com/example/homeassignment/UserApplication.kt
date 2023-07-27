package com.example.homeassignment

import android.app.Application
import com.example.homeassignment.di.DaggerNetworkComponent
import com.example.homeassignment.di.NetworkComponent

class UserApplication: Application() {
    lateinit var applicationComponent: NetworkComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerNetworkComponent.builder().build()
    }
}