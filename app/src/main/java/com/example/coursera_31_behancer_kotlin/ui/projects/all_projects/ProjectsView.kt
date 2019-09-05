package com.example.coursera_31_behancer_kotlin.ui.projects.all_projects

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.coursera_31_behancer_kotlin.ui.projects.BaseProjectsView

interface ProjectsView : BaseProjectsView {
    @StateStrategyType(SkipStrategy::class)
    fun openProfileFragment(username: String)
}