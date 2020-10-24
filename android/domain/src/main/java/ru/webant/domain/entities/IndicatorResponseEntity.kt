package ru.webant.domain.entities

import java.io.Serializable

data class IndicatorResponseEntity(
    var value: String?,
    var serialNumber: String?,
    val photoPath: String,
    var isSelected: Boolean = false
) : Serializable