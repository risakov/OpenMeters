package ru.webant.openmeters.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.webant.gateway.retrofit.Api
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class ApiModule {

    @Provides
    @Singleton
    fun provideScriptApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}