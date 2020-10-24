package ru.webant.openmeters.scenes.value_history

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
            if (it == indicatorType) {
                indicatorType.isSelected = true
            } else {
                it.isSelected = false
            }
        }
        viewState.updateIndicatorTypeAdapter()
    }

    private fun setIndicatorValues() {
        indicatorValues.add(IndicatorHistory(4))
        indicatorValues.add(IndicatorHistory(15))
        indicatorValues.add(IndicatorHistory(5))
        indicatorValues.add(IndicatorHistory(6))
        indicatorValues.add(IndicatorHistory(7))
        indicatorValues.add(IndicatorHistory(12))
        indicatorValues.add(IndicatorHistory(777))
        indicatorValues.add(IndicatorHistory(15))
        indicatorValues.add(IndicatorHistory(5))
        indicatorValues.add(IndicatorHistory(6))
        indicatorValues.add(IndicatorHistory(7))
        indicatorValues.add(IndicatorHistory(12))
        indicatorValues.add(IndicatorHistory(777))
    }

    private fun setIndicatorTypes() {
        indicatorTypes.add(IndicatorType.ColdWaterIndicator())
        indicatorTypes.add(IndicatorType.HotWaterIndicator())
        indicatorTypes.add(IndicatorType.ElectricIndicator())
    }
}