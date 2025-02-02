package ru.webant.openmeters.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.webant.domain.entities.IndicatorResponseEntity

@Parcelize
data class ParcelableIndicatorResponseEntity(
    val meterId: Int,
    var value: String?,
    var serialNumber: String?,
    val photoPath: String,
    var isSelected: Boolean = false
) : Parcelable {

    fun toIndicatorResultEntity(): IndicatorResponseEntity {
        return IndicatorResponseEntity(
            meterId = this.meterId,
            value = this.value,
            serialNumber = this.serialNumber,
            photoPath = this.photoPath,
            isSelected = this.isSelected
        )
    }


    companion object {

        fun fromIndicatorResponseEntity(indicatorResponse: IndicatorResponseEntity): ParcelableIndicatorResponseEntity {
            return ParcelableIndicatorResponseEntity(
                meterId = indicatorResponse.meterId,
                value = indicatorResponse.value,
                serialNumber = indicatorResponse.serialNumber,
                photoPath = indicatorResponse.photoPath,
                isSelected = indicatorResponse.isSelected
            )
        }
    }
}