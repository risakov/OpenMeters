package ru.webant.openmeters.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.webant.openmeters.scenes.main.MainActivity

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract val layoutId: Int
    open var toolbarLayoutId: Int = -1
    var toolbar: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        updateToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null
    }

    open fun setUpToolbar(toolbar: View?) {

    }

    fun updateToolbar() {
        setUpToolbar(toolbar)
        (activity as MainActivity).setUpActionBar(toolbar)
    }
}