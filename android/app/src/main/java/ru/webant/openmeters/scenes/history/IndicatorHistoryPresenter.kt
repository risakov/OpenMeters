package ru.webant.openmeters.scenes.history

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.models.IndicatorValue
import ru.webant.domain.models.IndicatorType
import ru.webant.domain.models.IndicatorType.Companion.COLD_WATER
import ru.webant.domain.models.IndicatorType.Companion.ELECTRICITY
import ru.webant.domain.models.IndicatorType.Companion.HOT_WATER
import ru.webant.openmeters.base.BasePresenter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@InjectViewState
class IndicatorHistoryPresenter @Inject constructor() : BasePresenter<IndicatorHistoryView>() {

    private val indicatorTypes = ArrayList<IndicatorType>()
    private val indicatorValues = ArrayList<IndicatorValue>()
    private lateinit var selectedIndicatorType: IndicatorType


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        setIndicatorTypes()
        getIndicatorsDependOnType()
        viewState.addIndicatorTypeAdapterItems(indicatorTypes)
        viewState.addIndicatorValuesAdapterItems(indicatorValues)
    }

    fun onIndicatorTypeClicked(indicatorType: IndicatorType) {
        indicatorTypes.forEach {
            it.isSelected = false
        }
        indicatorType.isSelected = true
        selectedIndicatorType = indicatorType
        viewState.updateIndicatorTypeAdapter()
        getIndicatorsDependOnType()
    }

    private fun getIndicatorsDependOnType() {
        when (selectedIndicatorType.title) {
            COLD_WATER -> getMockedIndicators()
            HOT_WATER -> getMockedIndicators()
            ELECTRICITY -> getMockedIndicators()
        }
    }

    private fun getMockedIndicators() {
        indicatorValues.clear()
        indicatorValues.add(IndicatorValue(getRandomId()))
        indicatorValues.add(IndicatorValue(getRandomId()))
        indicatorValues.add(IndicatorValue(getRandomId()))
        viewState.updateIndicatorValueAdapter()
    }

    private fun setIndicatorTypes() {
        indicatorTypes.add(IndicatorType.ColdWaterIndicator())
        indicatorTypes.add(IndicatorType.HotWaterIndicator())
        indicatorTypes.add(IndicatorType.ElectricIndicator())
        selectedIndicatorType = indicatorTypes[0]
    }

    private fun getRandomId(): Int {
        return Random().nextInt(1000)
    }
}