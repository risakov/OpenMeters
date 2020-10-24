package ru.webant.openmeters.scenes.history

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.models.IndicatorHistory
import ru.webant.domain.models.IndicatorType
import ru.webant.openmeters.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class IndicatorHistoryPresenter @Inject constructor() : BasePresenter<IndicatorHistoryView>() {

    private val indicatorTypes = ArrayList<IndicatorType>()
    private val indicatorValues = ArrayList<IndicatorHistory>()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setIndicatorTypes()
        setIndicatorValues()
        viewState.addIndicatorTypeAdapterItems(indicatorTypes)
        viewState.addIndicatorValuesAdapterItems(indicatorValues)
    }

    fun onIndicatorTypeClicked(indicatorType: IndicatorType) {
        indicatorTypes.forEach {
            it.isSelected = false
        }
        indicatorType.isSelected = true
        viewState.updateIndicatorTypeAdapter()
    }

    private fun setIndicatorValues() {
        indicatorValues.add(IndicatorHistory(4))
        indicatorValues.add(IndicatorHistory(15))
        indicatorValues.add(IndicatorHistory(5))
    }

    private fun setIndicatorTypes() {
        indicatorTypes.add(IndicatorType.ColdWaterIndicator())
        indicatorTypes.add(IndicatorType.HotWaterIndicator())
        indicatorTypes.add(IndicatorType.ElectricIndicator())
    }
}