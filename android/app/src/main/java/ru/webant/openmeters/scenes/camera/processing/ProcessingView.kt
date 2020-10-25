package ru.webant.openmeters.scenes.camera.processing

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.openmeters.base.BaseView
import ru.webant.openmeters.models.ParcelableIndicatorResponseEntity

interface ProcessingView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openResultFragment(indicatorResults: ArrayList<ParcelableIndicatorResponseEntity>)
}