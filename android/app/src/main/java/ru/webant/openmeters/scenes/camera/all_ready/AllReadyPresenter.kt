package ru.webant.openmeters.scenes.camera.all_ready

import com.arellomobile.mvp.InjectViewState
import ru.webant.openmeters.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class AllReadyPresenter @Inject constructor() : BasePresenter<AllReadyView>() {

    fun onNavigateHistoryButtonClicked() {
        viewState.navigateToHistoryFragment()
    }
}