package ru.webant.openmeters.scenes.camera.result

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.base.BaseView

interface ResultView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecyclerView(indicatorResults: ArrayList<IndicatorResponseEntity>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initIndicatorInfo(indicatorResult: IndicatorResponseEntity)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeButtonClickableState(state: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeMetersErrorVisibility(state: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun updateIndicatorResultAdapter()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearEditTextFocuses()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToAllReadyFragment()
}