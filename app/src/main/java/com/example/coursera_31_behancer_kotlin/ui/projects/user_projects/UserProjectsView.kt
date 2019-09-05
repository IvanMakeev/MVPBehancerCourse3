package com.example.coursera_31_behancer_kotlin.ui.projects.user_projects

import com.example.coursera_31_behancer_kotlin.data.model.project.Project
import com.example.coursera_31_behancer_kotlin.ui.projects.BaseProjectsView

interface UserProjectsView : BaseProjectsView {
    override fun showProjects(projects: List<Project>)
}