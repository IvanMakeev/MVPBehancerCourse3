package com.example.coursera_31_behancer_kotlin.ui.projects

import com.example.coursera_31_behancer_kotlin.common.BaseView
import com.example.coursera_31_behancer_kotlin.data.model.project.Project

interface BaseProjectsView : BaseView {
    fun showProjects(projects: List<Project>)
}