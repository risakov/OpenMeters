package ru.webant.openmeters.scenes.camera.result

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_result.*
import ru.webant.domain.entities.IndicatorResponseEntity
import ru.webant.openmeters.App
import ru.webant.openmeters.R
import ru.webant.openmeters.base.BaseFragment
import ru.webant.openmeters.extensions.setIsVisible
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

    override fun initIndicatorInfo(indicatorResult: IndicatorResponseEntity) {
        if (indicatorResult.value == null) {
            meterReadingsEditText.setText(resources.getString(R.string.could_not_recognize))
            meterReadingErrorTextView.setIsVisible(true)
            meterReadingsTextInputLayout.setBackgroundResource(R.drawable.bg_text_input_layout_red)
        } else {
            meterReadingsEditText.setText(indicatorResult.value)
            meterReadingErrorTextView.setIsVisible(false)
            meterReadingsTextInputLayout.setBackgroundResource(R.drawable.bg_text_input_layout_celestial)
        }
        if (indicatorResult.serialNumber == null) {
            serialNumberEditText.setText(resources.getString(R.string.could_not_recognize))
        } else {
            serialNumberEditText.setText(indicatorResult.serialNumber)
        }
    }

    override fun clearEditTextFocuses() {
        meterReadingsEditText.clearFocus()
        serialNumberEditText.clearFocus()
    }

    override fun changeMetersErrorVisibility(state: Boolean) {
        if (state) {
            meterReadingErrorTextView.setIsVisible(true)
            meterReadingsTextInputLayout.setBackgroundResource(R.drawable.bg_text_input_layout_red)
            adapter.notifyDataSetChanged()
        } else {
            meterReadingErrorTextView.setIsVisible(false)
            meterReadingsTextInputLayout.setBackgroundResource(R.drawable.bg_text_input_layout_celestial)
        }
    }

    override fun updateIndicatorResultAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun navigateToAllReadyFragment() {
        ResultFragmentDirections.openAllReadyFragment()
            .let(findNavController()::navigate)
    }

    override fun changeButtonClickableState(state: Boolean) {
        confirmButton.isEnabled = state
        if (state) {
            confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorLightBlue))
            confirmButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        } else {
            confirmButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPreLightGray))
            confirmButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorDarkGray))
        }
    }

    private fun setListeners() {
        confirmButton.setOnClickListener {
            presenter.onConfirmButtonClicked()
        }

        meterReadingsEditText.onFocusChangeListener = View.OnFocusChangeListener { p0, hasFocus ->
            val currentText = meterReadingsEditText.text.toString()
            if (hasFocus) {
                if (!currentText.contains(Regex("^[0-9]+\$")) && currentText.isNotBlank()) {
                    meterReadingsEditText.setText("")
                }
            } else {
                presenter.onFocusChanged(currentText)
            }
        }

        meterReadingsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newString: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newString: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (meterReadingsEditText.hasFocus()) {
                    presenter.onMeterReadingsChanged(newString.toString())
                }
            }
        })

        serialNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(newString: Editable?) {
                if (serialNumberEditText.hasFocus() && newString!!.isNotEmpty()) {
                    if (newString.contains(Regex("^[0-9]+\$"))) {
                        presenter.onSerialNumberChanged(newString.toString())
                    } else {
                        serialNumberEditText.setText("")
                    }
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(newString: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}