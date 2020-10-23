package ru.webant.openmeters.scenes.camera

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface CameraView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeFlashLightState(isNeedToTurnOn: Boolean)
}