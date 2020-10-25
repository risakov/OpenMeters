package ru.webant.openmeters.scenes.camera.result.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_indicator_result.view.*
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.gateway.constants.ApiConstants
import ru.webant.openmeters.R
import ru.webant.openmeters.extensions.setIsVisible
import ru.webant.openmeters.scenes.camera.result.adapter.IndicatorResultAdapter

class IndicatorResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var callback: IndicatorResultAdapter.Callback
    private lateinit var indicatorResult: IndicatorResponseEntity


    init {
        view.setOnClickListener {
            callback.onIndicatorResultClicked(indicatorResult)
        }
    }

    fun bind(indicatorResult: IndicatorResponseEntity, callback: IndicatorResultAdapter.Callback) {
        this.callback = callback
        this.indicatorResult = indicatorResult
        view.bindIsSelected()
        view.bindErrorImage()
        view.bindImage()
    }

    private fun View.bindImage() {
        Glide.with(this)
            .load("${ApiConstants.API_URL}${indicatorResult.photoPath}")
            .placeholder(R.drawable.ic_small_placeholder)
            .into(indicatorResultImageView)
    }

    private fun View.bindErrorImage() {
        if (indicatorResult.value == null) {
            meterProcessingErrorImageView.setIsVisible(true)
        } else {
            meterProcessingErrorImageView.setIsVisible(false)
        }
    }

    private fun View.bindIsSelected() {
        if (indicatorResult.isSelected) {
            indicatorResultImageView.setBackgroundResource(R.drawable.bg_rounded_image_light_blue)
        } else {
            indicatorResultImageView.setBackgroundResource(R.drawable.bg_rounded_image_gray)
        }
    }
}