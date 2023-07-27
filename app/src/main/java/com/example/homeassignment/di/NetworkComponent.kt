package com.example.homeassignment.di

import com.example.homeassignment.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(mainActivity: MainActivity)
}