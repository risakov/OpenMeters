package ru.webant.openmeters.scenes.camera.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_result.*
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.scenes.camera.result.adapter.IndicatorResultAdapter

class ResultFragment : BaseFragment(), ResultView {

    override val layoutId = R.layout.fragment_result
    override var isNeedToShowBottomNavigationView = false

    private val adapter = IndicatorResultAdapter()

    @InjectPresenter
    lateinit var presenter: ResultPresenter


    @ProvidePresenter
    fun providePresenter(): ResultPresenter = App.appComponent.provideResultPresenter()

    override fun initRecyclerView(indicatorResults: ArrayList<IndicatorResponseEntity>) {
        adapter.addItems(indicatorResults)
        adapter.setCallback(object : IndicatorResultAdapter.Callback {
            override fun onIndicatorResultClicked(indicatorResult: IndicatorResponseEntity) {
                presenter.onIndicatorResultClicked(indicatorResult)
            }
        })
        indicatorsRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun updateIndicatorResultAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun navigateToAllReadyFragment() {
        ResultFragmentDirections.openAllReadyFragment()
            .let(findNavController()::navigate)
    }

    private fun setListeners() {
        confirmButton.setOnClickListener {
            presenter.onConfirmButtonClicked()
        }
    }
}