package ru.webant.gateway.retrofit

import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.webant.domain.models.IndicatorResponseEntity
import ru.webant.domain.models.ResponseEntity

interface Api {

    @Multipart
    @POST("/uploadImage")
    fun uploadFiles(@Part files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>>
}