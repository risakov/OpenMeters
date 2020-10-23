package ru.webant.gateway.retrofit.gateways

import io.reactivex.Single
import okhttp3.MultipartBody
import ru.webant.domain.gateways.IndicatorGateway
import ru.webant.domain.models.IndicatorResponseEntity
import ru.webant.domain.models.ResponseEntity
import ru.webant.gateway.retrofit.Api

class RetrofitIndicatorGateway(private val api: Api) : IndicatorGateway {

    override fun uploadFiles(files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>> =
        api.uploadFiles(files)
}