package ru.webant.openmeters.scenes.camera.processing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_processing.*
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment

class ProcessingFragment : BaseFragment(), ProcessingView {

    override val layoutId = R.layout.fragment_processing
    override var isNeedToShowBottomNavigationView = false

    private val args: ProcessingFragmentArgs by navArgs()


    @InjectPresenter
    lateinit var presenter: ProcessingPresenter

    @ProvidePresenter
    fun providePresenter(): ProcessingPresenter = App.appComponent.provideProcessingPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.filePaths = args.filePaths.toCollection(ArrayList())
        setListeners()
    }

    override fun showToast(filePaths: ArrayList<String>) {
        Toast.makeText(requireContext(), "Zhopa ${filePaths.size}", Toast.LENGTH_SHORT).show()
    }

    private fun setListeners() {
        processingResultTextView.setOnClickListener {
            ProcessingFragmentDirections.openResultFragment()
                .let(findNavController()::navigate)
        }
    }
}