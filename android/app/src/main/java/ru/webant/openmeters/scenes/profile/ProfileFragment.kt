package ru.webant.openmeters.scenes.profile

import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.toolbar.view.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment

class ProfileFragment : BaseFragment(), ProfileView {

    override val layoutId = R.layout.fragment_profile

    override var toolbarLayoutId = R.layout.toolbar

    @InjectPresenter
    lateinit var presenter: ProfilePresenter


    @ProvidePresenter
    fun providePresenter(): ProfilePresenter = App.appComponent.provideProfilePresenter()

    override fun setUpToolbar(toolbar: View?) {
        toolbar?.let {
            it.toolbarTitle.text = resources.getString(R.string.profile)
        }
    }
}