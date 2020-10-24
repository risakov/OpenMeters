package ru.webant.openmeters.scenes.camera.processing

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.openmeters.base.BaseView

interface ProcessingView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(filePaths: ArrayList<String>)
}