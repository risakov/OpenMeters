package ru.webant.openmeters.scenes.value_history.adapter.holders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_indicator_type.view.*
import ru.webant.domain.models.IndicatorType
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.value_history.adapter.IndicatorTypeAdapter

class IndicatorTypeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var indicatorType: IndicatorType
    private lateinit var callback: IndicatorTypeAdapter.Callback


    init {
        view.setOnClickListener {
            callback.onIndicatorTypeClicked(indicatorType)
        }
    }

    fun bind(indicatorType: IndicatorType, callback: IndicatorTypeAdapter.Callback) {
        this.callback = callback
        this.indicatorType = indicatorType
        view.bindIndicatorTitle(indicatorType)
        view.bindIsSelected(indicatorType)
    }

    private fun View.bindIndicatorTitle(indicatorType: IndicatorType) {
        indicatorTypeTitleTextView.text = indicatorType.title
    }

    private fun View.bindIsSelected(indicatorType: IndicatorType) {
        if (indicatorType.isSelected) {
            indicatorTypeContainer.setBackgroundResource(R.drawable.bg_rounded_light_blue)
            indicatorTypeTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.colorLightBlack))
        } else {
            indicatorTypeContainer.setBackgroundResource(R.drawable.bg_rounded_white_with_light_gray_border)
            indicatorTypeTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.colorLightBlack))
        }
    }
}