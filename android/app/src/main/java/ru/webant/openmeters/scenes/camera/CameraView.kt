package ru.webant.openmeters.scenes.camera

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.openmeters.base.BaseView

interface CameraView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeFlashLightState()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun takePicture()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun getImagesFromGallery()
}