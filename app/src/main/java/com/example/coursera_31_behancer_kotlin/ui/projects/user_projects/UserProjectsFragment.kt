package com.example.coursera_31_behancer_kotlin.ui.projects.user_projects

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.coursera_31_behancer_kotlin.R
import com.example.coursera_31_behancer_kotlin.ui.projects.BaseProjectsFragment
import com.example.coursera_31_behancer_kotlin.ui.projects.ProjectsAdapter


class UserProjectsFragment : BaseProjectsFragment(), UserProjectsView {

    companion object {
        const val USER_ID = "USER_ID"

        fun newInstance(args: Bundle): UserProjectsFragment {
            val fragment = UserProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var username: String
    @InjectPresenter
    internal lateinit var localPresenter: UserProjectsPresenter

    @ProvidePresenter
    fun providePresenter() = UserProjectsPresenter(storage!!)

    override fun getPresenter(): UserProjectsPresenter {
        return localPresenter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            username = it.getString(USER_ID)!!
        }

        activity?.let {
            it.title = getString(R.string.projects) + " $username"
        }
        projectsAdapter = ProjectsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = projectsAdapter

        onRefreshData()
    }

    override fun onRefreshData() {
        localPresenter.getUserProjects(username)
    }
}
