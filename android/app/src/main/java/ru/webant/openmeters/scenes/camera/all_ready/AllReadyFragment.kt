package ru.webant.openmeters.scenes.camera.all_ready

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_all_ready.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.scenes.main.MainActivity

class AllReadyFragment : BaseFragment(), AllReadyView {

    override val layoutId = R.layout.fragment_all_ready
    override var isNeedToShowBottomNavigationView = false

    @InjectPresenter
    lateinit var presenter: AllReadyPresenter


    @ProvidePresenter
    fun providePresenter(): AllReadyPresenter = App.appComponent.provideAllReadyPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun navigateToHistoryFragment() {
        (activity as MainActivity).navigateToHistoryFragment()
    }

    private fun setListeners() {
        navigateToHistoryButton.setOnClickListener {
            presenter.onNavigateHistoryButtonClicked()
        }
    }
}