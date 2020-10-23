package ru.webant.openmeters.scenes.value_history

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_value_history.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment

class ValueHistoryFragment : BaseFragment(), ValueHistoryView {

    override val layoutId = R.layout.fragment_value_history

    @InjectPresenter
    lateinit var presenter: ValueHistoryPresenter


    @ProvidePresenter
    fun providePresenter(): ValueHistoryPresenter = App.appComponent.provideValueHistoryPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}