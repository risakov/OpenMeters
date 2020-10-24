package ru.webant.domain.entities

import java.io.Serializable

data class IndicatorResponseEntity(
    val meterId: Int,
    var value: String?,
    var serialNumber: String?,
    val photoPath: String,
    var isSelected: Boolean = false
) : Serializable