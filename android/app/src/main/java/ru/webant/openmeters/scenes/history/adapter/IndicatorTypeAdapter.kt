package ru.webant.openmeters.scenes.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.webant.domain.models.IndicatorType
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.history.adapter.holders.IndicatorTypeViewHolder

class IndicatorTypeAdapter : RecyclerView.Adapter<IndicatorTypeViewHolder>() {

    private var indicatorTypes = ArrayList<IndicatorType>()
    private lateinit var callback: Callback

    interface Callback {
        fun onIndicatorTypeClicked(indicatorType: IndicatorType)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorTypeViewHolder {
        return IndicatorTypeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_indicator_type, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return indicatorTypes.size
    }

    override fun onBindViewHolder(holder: IndicatorTypeViewHolder, position: Int) {
        holder.bind(indicatorTypes[position], callback)
    }

    fun addItems(indicatorTypes: ArrayList<IndicatorType>) {
        this.indicatorTypes = indicatorTypes
        notifyDataSetChanged()
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }
}