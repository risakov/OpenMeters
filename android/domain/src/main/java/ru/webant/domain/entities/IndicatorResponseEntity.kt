package ru.webant.domain.entities

data class IndicatorResponseEntity(
    var value: String?,
    var serialNumber: String?,
    val photoPath: String,
    var isSelected: Boolean = false
)