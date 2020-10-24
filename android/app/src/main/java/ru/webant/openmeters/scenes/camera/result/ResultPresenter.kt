package ru.webant.openmeters.scenes.camera.result

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor() : BasePresenter<ResultView>() {

    private val indicatorResults = ArrayList<IndicatorResponseEntity>()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initIndicatorResults()
        viewState.initRecyclerView(indicatorResults)
    }

    fun onIndicatorResultClicked(indicatorResult: IndicatorResponseEntity) {
        indicatorResults.forEach {
            it.isSelected = false
        }
        indicatorResult.isSelected = true
        viewState.updateIndicatorResultAdapter()
    }

    fun onConfirmButtonClicked() {
        viewState.navigateToAllReadyFragment()
    }

    private fun initIndicatorResults() {
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd", true))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
        indicatorResults.add(IndicatorResponseEntity("asd", "asd", "asdasd"))
    }
}