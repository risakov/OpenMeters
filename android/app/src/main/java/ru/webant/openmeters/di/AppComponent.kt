package ru.webant.openmeters.di

import dagger.Component
import ru.webant.openmeters.scenes.main.MainPresenter
import ru.webant.openmeters.scenes.value_history.IndicatorHistoryPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class])
interface AppComponent {

    fun provideMainPresenter(): MainPresenter
    fun provideValueHistoryPresenter(): IndicatorHistoryPresenter
}