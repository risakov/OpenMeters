package ru.webant.domain.entities.post

import com.google.gson.annotations.SerializedName

data class PostIndicatorValue(
    @SerializedName("meter_id")
    val meterId: Int,
    val value: String
)