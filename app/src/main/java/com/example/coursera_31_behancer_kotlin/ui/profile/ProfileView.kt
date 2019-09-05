package com.example.coursera_31_behancer_kotlin.ui.profile

import com.example.coursera_31_behancer_kotlin.common.BaseView
import com.example.coursera_31_behancer_kotlin.data.model.user.User

interface ProfileView : BaseView {

    fun showProfile()

    fun bind(user: User)
}