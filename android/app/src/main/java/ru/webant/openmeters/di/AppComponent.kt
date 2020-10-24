package ru.webant.openmeters.di

import dagger.Component
import ru.webant.openmeters.scenes.camera.all_ready.AllReadyPresenter
import ru.webant.openmeters.scenes.camera.processing.ProcessingPresenter
import ru.webant.openmeters.scenes.camera.result.ResultPresenter
import ru.webant.openmeters.scenes.history.IndicatorHistoryPresenter
import ru.webant.openmeters.scenes.main.MainPresenter
import ru.webant.openmeters.scenes.profile.ProfilePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class])
interface AppComponent {

    fun provideMainPresenter(): MainPresenter
    fun provideIndicatorHistoryPresenter(): IndicatorHistoryPresenter
    fun provideProfilePresenter(): ProfilePresenter
    fun provideProcessingPresenter(): ProcessingPresenter
    fun provideResultPresenter(): ResultPresenter
    fun provideAllReadyPresenter(): AllReadyPresenter
}