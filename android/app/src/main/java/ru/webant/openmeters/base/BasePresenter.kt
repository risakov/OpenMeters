package ru.webant.openmeters.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    protected val compositeDisposable = CompositeDisposable()


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}