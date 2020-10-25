package ru.webant.openmeters.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.webant.openmeters.R
import ru.webant.openmeters.scenes.main.MainActivity

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract val layoutId: Int
    open var toolbarLayoutId = -1
    open var isNeedToShowBottomNavigationView = true
    var toolbar: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = if (toolbarLayoutId != -1) {
            LayoutInflater.from(activity).inflate(toolbarLayoutId, null)
        } else {
            null
        }
    }

    override fun onResume() {
        super.onResume()
        changeBottomNavigationBarVisibility()
        updateToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null
    }

    override fun showMessageWithRouteToHistoryFragment() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.error)
            .setMessage(R.string.backend_error_happened)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                openHistoryFragment()
            }
            .create()
            .show()
    }

    override fun openHistoryFragment() {
        (activity as MainActivity).navigateToHistoryFragment()
    }

    open fun setUpToolbar(toolbar: View?) {

    }

    private fun changeBottomNavigationBarVisibility() {
        (activity as MainActivity).changeBottomNavigationBarVisibility(
            isNeedToShowBottomNavigationView
        )
    }

    fun updateToolbar() {
        setUpToolbar(toolbar)
        (activity as MainActivity).setUpActionBar(toolbar)
    }
}