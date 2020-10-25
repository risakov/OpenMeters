package ru.webant.openmeters.scenes.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.webant.domain.models.IndicatorValue
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.history.adapter.holders.IndicatorHistoryViewHolder

class IndicatorHistoryAdapter : RecyclerView.Adapter<IndicatorHistoryViewHolder>() {

    private var indicatorValues = ArrayList<IndicatorValue>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorHistoryViewHolder {
        return IndicatorHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_indicator_history, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return indicatorValues.size
    }

    override fun onBindViewHolder(holder: IndicatorHistoryViewHolder, position: Int) {
        holder.bind(indicatorValues[position])
    }

    fun setItems(indicatorValue: ArrayList<IndicatorValue>) {
        this.indicatorValues = indicatorValue
        notifyDataSetChanged()
    }
}