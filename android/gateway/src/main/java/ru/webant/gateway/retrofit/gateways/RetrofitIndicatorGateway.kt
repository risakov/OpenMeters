package ru.webant.gateway.retrofit.gateways

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.domain.entities.ResponseEntity
import ru.webant.domain.entities.post.PostIndicatorValue
import ru.webant.domain.gateways.IndicatorGateway
import ru.webant.gateway.retrofit.Api

class RetrofitIndicatorGateway(private val api: Api) : IndicatorGateway {

    override fun uploadFiles(files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>> =
        api.uploadFiles(files)

    override fun uploadFile(file: MultipartBody.Part): Single<ResponseEntity<IndicatorResponseEntity>> =
        api.uploadFile(file)

    override fun createIndicatorValue(indicatorValue: PostIndicatorValue): Completable =
        api.createIndicatorValue(indicatorValue)
}