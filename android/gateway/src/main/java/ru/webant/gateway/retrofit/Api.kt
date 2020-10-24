package ru.webant.gateway.retrofit

import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.domain.entities.ResponseEntity

interface Api {

    @Multipart
    @POST("/api/uploadImages")
    fun uploadFiles(@Part files: List<MultipartBody.Part>): Single<ResponseEntity<IndicatorResponseEntity>>

    @Multipart
    @POST("/api/uploadSingleImage")
    fun uploadFile(@Part file: MultipartBody.Part): Single<ResponseEntity<IndicatorResponseEntity>>
}