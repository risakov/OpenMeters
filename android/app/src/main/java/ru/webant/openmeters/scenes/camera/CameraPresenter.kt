package ru.webant.openmeters.scenes.camera

import com.arellomobile.mvp.InjectViewState
import ru.webant.openmeters.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class CameraPresenter @Inject constructor() : BasePresenter<CameraView>() {

    fun onLeftArrowClicked() {
        viewState.openHistoryFragment()
    }

    fun onTakePictureClicked() {
        viewState.takePicture()
    }

    fun onFlashLightClicked() {
        viewState.changeFlashLightState()
    }

    fun onGetImageClicked() {
        viewState.getImagesFromGallery()
    }
}