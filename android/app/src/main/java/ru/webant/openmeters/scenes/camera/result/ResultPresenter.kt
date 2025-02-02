package ru.webant.openmeters.scenes.camera.result

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.domain.entities.post.PostIndicatorValue
import ru.webant.domain.gateways.IndicatorGateway
import ru.webant.openmeters.base.BasePresenter
import ru.webant.openmeters.models.ParcelableIndicatorResponseEntity
import javax.inject.Inject

@InjectViewState
class ResultPresenter @Inject constructor(
    private val indicatorGateway: IndicatorGateway
) : BasePresenter<ResultView>() {

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
        if (indicatorResults.size == 1) {
            indicatorGateway.createIndicatorValue(
                PostIndicatorValue(
                    chosenIndicator.meterId,
                    chosenIndicator.value!!
                )
            )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    viewState.changeLoaderState(true)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    viewState.changeLoaderState(false)
                }
                .subscribe({
                    viewState.navigateToAllReadyFragment()
                },{
                    viewState.showMessageWithRouteToHistoryFragment()
                })
                .addTo(compositeDisposable)
        } else {
            viewState.navigateToAllReadyFragment()
        }
    }

    fun onMeterReadingsChanged(newValue: String) {
        if (newValue.isBlank()) {
            chosenIndicator.value = null
            viewState.changeMetersErrorVisibility(true)
        } else {
            chosenIndicator.value = newValue
            viewState.changeMetersErrorVisibility(false)
        }
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
