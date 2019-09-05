package com.example.coursera_31_behancer_kotlin.common

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>(){

    protected val compositeDisposable = CompositeDisposable()

    fun disposeAll() {
        compositeDisposable.dispose()
    }
}

