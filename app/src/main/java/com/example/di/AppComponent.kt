package com.example.di

import com.example.CovidApp
import com.example.remote.data.di.CovidRemoteModule
import com.example.remote.di.RemoteModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RemoteModule::class,
        CovidRemoteModule::class,
    ]
)

@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: CovidApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: CovidApp)
}