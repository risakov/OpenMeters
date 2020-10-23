package ru.webant.domain.gateways

import io.reactivex.Single
import okhttp3.MultipartBody
import ru.webant.domain.models.IndicatorResponseEntity
import ru.webant.domain.models.ResponseEntity

interface IndicatorGateway {

    fun uploadFiles(files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>>
}