package ru.webant.domain.gateways

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.domain.entities.ResponseEntity
import ru.webant.domain.entities.post.PostIndicatorValue

interface IndicatorGateway {

    fun uploadFiles(files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>>
    fun uploadFile(file: MultipartBody.Part): Single<ResponseEntity<IndicatorResponseEntity>>
    fun createIndicatorValue(indicatorValue: PostIndicatorValue): Completable
}