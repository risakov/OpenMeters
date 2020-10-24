package ru.webant.openmeters.scenes.history.adapter.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_indicator_history.view.*
import ru.webant.domain.models.IndicatorHistory

class IndicatorHistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(indicatorHistory: IndicatorHistory) {
        view.indicatorIdTextView.text = indicatorHistory.id.toString()
    }
}