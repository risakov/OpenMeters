package ru.webant.openmeters.scenes.camera.result

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor() : BasePresenter<ResultView>() {

    private val indicatorResults = ArrayList<IndicatorResponseEntity>()
    private lateinit var chosenIndicator: IndicatorResponseEntity


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initIndicatorResults()
        viewState.initRecyclerView(indicatorResults)
        viewState.initIndicatorInfo(chosenIndicator)
        isCannotConfirmResult()
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

    fun onSerialNumberChanged(newSerialNumber: String) {
        chosenIndicator.serialNumber = newSerialNumber
        isCannotConfirmResult()
    }

    private fun initIndicatorResults() {
        indicatorResults.add(IndicatorResponseEntity(null, "qqweqweq", "asdasd", true))
        indicatorResults.add(IndicatorResponseEntity(null, "tttttt", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity(null, null, "asdasd"))
        indicatorResults.add(IndicatorResponseEntity(null, null, "asdasd"))
        chosenIndicator = indicatorResults[0]
    }

    private fun isCannotConfirmResult() {
        if (indicatorResults.all { it.value == null }) {
            viewState.changeButtonClickableState(false)
        } else {
            viewState.changeButtonClickableState(true)
        }
    }
}