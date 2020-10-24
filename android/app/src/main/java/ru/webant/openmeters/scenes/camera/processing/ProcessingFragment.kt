package ru.webant.openmeters.scenes.camera.processing

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_processing.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment

class ProcessingFragment : BaseFragment(), ProcessingView {

    override val layoutId = R.layout.fragment_processing
    override var isNeedToShowBottomNavigationView = false


    @InjectPresenter
    lateinit var presenter: ProcessingPresenter

    @ProvidePresenter
    fun providePresenter(): ProcessingPresenter = App.appComponent.provideProcessingPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        processingResultTextView.setOnClickListener {
            ProcessingFragmentDirections.openResultFragment()
                .let(findNavController()::navigate)
        }
    }
}