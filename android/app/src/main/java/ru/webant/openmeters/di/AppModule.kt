package ru.webant.openmeters.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.webant.openmeters.App
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun providesApplication(): App = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application.applicationContext
    }
}