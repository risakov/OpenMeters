package ru.webant.openmeters.scenes.history

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.domain.models.IndicatorType
import ru.webant.domain.models.IndicatorHistory
import ru.webant.openmeters.base.BaseView

interface IndicatorHistoryView : BaseView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addIndicatorTypeAdapterItems(indicatorTypes: ArrayList<IndicatorType>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun addIndicatorValuesAdapterItems(indicatorHistories: ArrayList<IndicatorHistory>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateIndicatorTypeAdapter()
}