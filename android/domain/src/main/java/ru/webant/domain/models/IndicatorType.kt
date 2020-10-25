package ru.webant.domain.models

sealed class IndicatorType(open val title: String, open var isSelected: Boolean) {

    data class ColdWaterIndicator(
        override val title: String = "Горячая вода",
        override var isSelected: Boolean = true
    ) : IndicatorType(title, isSelected)

    data class HotWaterIndicator(
        override val title: String = "Холодная вода",
        override var isSelected: Boolean = false
    ) : IndicatorType(title, isSelected)

    data class ElectricIndicator(
        override val title: String = "Электричество",
        override var isSelected: Boolean = false
    ) : IndicatorType(title, isSelected)

    companion object {
        const val HOT_WATER = "Горячая вода"
        const val COLD_WATER = "Холодная вода"
        const val ELECTRICITY = "Электричество"
    }
}
