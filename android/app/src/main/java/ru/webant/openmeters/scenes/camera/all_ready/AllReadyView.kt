package ru.webant.openmeters.scenes.camera.all_ready

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.openmeters.base.BaseView

interface AllReadyView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToHistoryFragment()
}