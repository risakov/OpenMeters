package ru.webant.openmeters.scenes.camera.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.camera.result.adapter.holders.IndicatorResultViewHolder

class IndicatorResultAdapter : RecyclerView.Adapter<IndicatorResultViewHolder>() {

    interface Callback {
        fun onIndicatorResultClicked(indicatorResult: IndicatorResponseEntity)
    }


    private var indicatorResults = ArrayList<IndicatorResponseEntity>()
    private lateinit var callback: Callback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorResultViewHolder {
        return IndicatorResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_indicator_result, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return indicatorResults.size
    }

    override fun onBindViewHolder(holder: IndicatorResultViewHolder, position: Int) {
        holder.bind(indicatorResults[position], callback)
    }

    fun addItems(indicatorResults: ArrayList<IndicatorResponseEntity>) {
        this.indicatorResults = indicatorResults
        notifyDataSetChanged()
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }
}