package ru.webant.openmeters

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.webant.openmeters.di.AppComponent
import ru.webant.openmeters.di.AppModule
import ru.webant.openmeters.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}