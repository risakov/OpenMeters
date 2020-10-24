package ru.webant.domain.entities

data class IndicatorResponseEntity(
    val value: String,
    val serialNumber: String,
    val photoPath: String,
    var isSelected: Boolean = false
)