package ru.webant.openmeters.di

import dagger.Component
import ru.webant.openmeters.scenes.main.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {

    fun provideMainPresenter(): MainPresenter
}