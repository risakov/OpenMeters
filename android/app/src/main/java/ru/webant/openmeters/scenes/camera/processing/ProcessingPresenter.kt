package ru.webant.openmeters.scenes.camera.processing

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.webant.domain.gateways.IndicatorGateway
import ru.webant.openmeters.base.BasePresenter
import java.io.File
import javax.inject.Inject

@InjectViewState
class ProcessingPresenter @Inject constructor(
    private val indicatorGateway: IndicatorGateway
) : BasePresenter<ProcessingView>() {

    lateinit var filePaths: ArrayList<String>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (filePaths.size == 1) {
            loadSingleImage()
        } else {
            loadMultiplyImages()
        }
    }

    private fun loadSingleImage() {
        val file = File(filePaths[0])
        val filePart = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        val part = MultipartBody.Part.createFormData("file", file.name, filePart)

        indicatorGateway.uploadFile(part)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    private fun loadMultiplyImages() {
        val multiparts = ArrayList<MultipartBody.Part>()
        filePaths.forEach {
            val file = File(it)
            val filePart = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())

            multiparts.add(MultipartBody.Part.createFormData("photos", file.name, filePart))
        }

        indicatorGateway.uploadFiles(multiparts)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }
}