package com.example.coursera_31_behancer_kotlin.common

import com.arellomobile.mvp.MvpAppCompatFragment

abstract class PresenterFragment : MvpAppCompatFragment() {

    protected abstract fun getPresenter(): BasePresenter<*>

    override fun onDetach() {
        getPresenter().disposeAll()
        super.onDetach()
    }
}