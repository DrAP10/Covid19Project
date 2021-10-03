package com.example.di

import android.app.Application
import android.content.Context
import com.example.CovidApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: CovidApp): Context = app

    @Provides
    @Singleton
    fun provideApplication(app: CovidApp): Application = app

}