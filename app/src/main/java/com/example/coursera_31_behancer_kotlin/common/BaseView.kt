package com.example.coursera_31_behancer_kotlin.common

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView{

    fun showRefresh()

    fun hideRefresh()

    fun showError()
}