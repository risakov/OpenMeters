package ru.webant.openmeters.scenes.profile

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment

class ProfileFragment : BaseFragment(), ProfileView {

    override val layoutId = R.layout.fragment_profile

    @InjectPresenter
    lateinit var presenter: ProfilePresenter


    @ProvidePresenter
    fun providePresenter(): ProfilePresenter = App.appComponent.provideProfilePresenter()
}