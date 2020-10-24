package ru.webant.openmeters.scenes.camera.result

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.base.BasePresenter
import ru.webant.openmeters.models.ParcelableIndicatorResponseEntity
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor() : BasePresenter<ResultView>() {

    lateinit var indicatorResults: ArrayList<IndicatorResponseEntity>
    private lateinit var chosenIndicator: IndicatorResponseEntity


    fun parseParcelableIndicator(parcelableIndicators: ArrayList<ParcelableIndicatorResponseEntity>) {
        indicatorResults = parcelableIndicators.map { it.toIndicatorResultEntity() } as ArrayList
        chosenIndicator = indicatorResults[0].apply { isSelected = true }
        setInitialSettings()
    }

    fun onIndicatorResultClicked(indicatorResult: IndicatorResponseEntity) {
        indicatorResults.forEach {
            it.isSelected = false
        }
        indicatorResult.isSelected = true
        chosenIndicator = indicatorResult
        viewState.initIndicatorInfo(chosenIndicator)
        viewState.updateIndicatorResultAdapter()
        viewState.clearEditTextFocuses()
    }

    fun onConfirmButtonClicked() {
        viewState.navigateToAllReadyFragment()
    }

    fun onMeterReadingsChanged(newValue: String) {
        chosenIndicator.value = newValue
        viewState.changeMetersErrorVisibility(false)
        viewState.updateIndicatorResultAdapter()
        isCannotConfirmResult()
    }

    fun onFocusChanged(currentText: String) {
        if (currentText.isBlank()) {
            chosenIndicator.value = null
            viewState.changeMetersErrorVisibility(true)
        }
    }

    fun onLeftArrowClicked() {
        viewState.openHistoryFragment()
    }

    fun onSerialNumberChanged(newSerialNumber: String) {
        chosenIndicator.serialNumber = newSerialNumber
        isCannotConfirmResult()
    }

    private fun setInitialSettings() {
        viewState.initIndicatorInfo(chosenIndicator)
        viewState.initRecyclerView(indicatorResults)
        isCannotConfirmResult()
    }

    private fun isCannotConfirmResult() {
        if (indicatorResults.all { it.value == null }) {
            viewState.changeButtonClickableState(false)
        } else {
            viewState.changeButtonClickableState(true)
        }
    }
}
