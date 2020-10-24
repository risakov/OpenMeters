package ru.webant.openmeters.scenes.value_history

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_indicator_history.*
import ru.webant.domain.models.IndicatorHistory
import ru.webant.domain.models.IndicatorType
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.scenes.value_history.adapter.IndicatorHistoryAdapter
import ru.webant.openmeters.scenes.value_history.adapter.IndicatorTypeAdapter
import ru.webant.openmeters.scenes.value_history.adapter.decorators.IndicatorTypeItemDecorator

class IndicatorHistoryFragment : BaseFragment(), IndicatorHistoryView {

    override val layoutId = R.layout.fragment_indicator_history
    override var toolbarLayoutId = R.layout.toolbar

    @InjectPresenter
    lateinit var presenter: IndicatorHistoryPresenter

    private val indicatorTypeAdapter = IndicatorTypeAdapter()
    private val indicatorHistoryAdapter = IndicatorHistoryAdapter()


    @ProvidePresenter
    fun providePresenter(): IndicatorHistoryPresenter = App.appComponent.provideValueHistoryPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpListeners()
    }

    override fun addIndicatorValuesAdapterItems(indicatorHistories: ArrayList<IndicatorHistory>) {
        indicatorHistoryAdapter.setItems(indicatorHistories)
    }

    override fun addIndicatorTypeAdapterItems(indicatorTypes: ArrayList<IndicatorType>) {
        indicatorTypeAdapter.addItems(indicatorTypes)
    }

    override fun updateIndicatorTypeAdapter() {
        indicatorTypeAdapter.notifyDataSetChanged()
    }

    private fun setUpListeners() {
        indicatorTypeAdapter.setCallback(object : IndicatorTypeAdapter.Callback {
            override fun onIndicatorTypeClicked(indicatorType: IndicatorType) {
                presenter.onIndicatorTypeClicked(indicatorType)
            }
        })
    }

    private fun setUpRecyclerView() {
        indicatorTypesRecyclerView.apply {
            adapter = indicatorTypeAdapter
            addItemDecoration(IndicatorTypeItemDecorator())
        }
        indicatorHistoryRecyclerView.adapter = indicatorHistoryAdapter
    }
}