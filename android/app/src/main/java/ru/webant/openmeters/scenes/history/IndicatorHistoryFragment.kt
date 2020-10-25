package ru.webant.openmeters.scenes.history

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_indicator_history.*
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.webant.domain.models.IndicatorValue
import ru.webant.domain.models.IndicatorType
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.scenes.history.adapter.IndicatorHistoryAdapter
import ru.webant.openmeters.scenes.history.adapter.IndicatorTypeAdapter
import ru.webant.openmeters.scenes.history.adapter.decorators.IndicatorTypeItemDecorator

class IndicatorHistoryFragment : BaseFragment(), IndicatorHistoryView {

    override val layoutId = R.layout.fragment_indicator_history
    override var toolbarLayoutId = R.layout.toolbar

    @InjectPresenter
    lateinit var presenter: IndicatorHistoryPresenter

    private val indicatorTypeAdapter = IndicatorTypeAdapter()
    private val indicatorHistoryAdapter = IndicatorHistoryAdapter()


    @ProvidePresenter
    fun providePresenter(): IndicatorHistoryPresenter =
        App.appComponent.provideIndicatorHistoryPresenter()

    override fun setUpToolbar(toolbar: View?) {
        toolbar?.let {
            it.toolbarTitle.text = resources.getString(R.string.history)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpListeners()
    }

    override fun addIndicatorValuesAdapterItems(indicatorValues: ArrayList<IndicatorValue>) {
        indicatorHistoryAdapter.setItems(indicatorValues)
    }

    override fun addIndicatorTypeAdapterItems(indicatorTypes: ArrayList<IndicatorType>) {
        indicatorTypeAdapter.addItems(indicatorTypes)
    }

    override fun updateIndicatorTypeAdapter() {
        indicatorTypeAdapter.notifyDataSetChanged()
    }

    override fun updateIndicatorValueAdapter() {
        indicatorHistoryAdapter.notifyDataSetChanged()
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