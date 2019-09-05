package com.example.coursera_31_behancer_kotlin.ui.projects.all_projects

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.coursera_31_behancer_kotlin.R
import com.example.coursera_31_behancer_kotlin.ui.profile.ProfileActivity
import com.example.coursera_31_behancer_kotlin.ui.profile.ProfileFragment
import com.example.coursera_31_behancer_kotlin.ui.projects.BaseProjectsFragment
import com.example.coursera_31_behancer_kotlin.ui.projects.ProjectsAdapter

class ProjectsFragment : BaseProjectsFragment(), ProjectsView, ProjectsAdapter.OnItemClickListener {

    companion object {
        fun newInstance(): ProjectsFragment {
            return ProjectsFragment()
        }
    }

    @InjectPresenter
    internal lateinit var localPresenter: ProjectsPresenter

    @ProvidePresenter
    fun providePresenter() =
        ProjectsPresenter(this, storage!!)

    override fun getPresenter(): ProjectsPresenter {
        return localPresenter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.setTitle(R.string.projects)

        projectsAdapter = ProjectsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = projectsAdapter
        onRefreshData()
    }

    override fun onItemClick(username: String) {
        localPresenter.openProfileFragment(username)
    }

    override fun onRefreshData() {
        localPresenter.getProjects()
    }

    override fun openProfileFragment(username: String) {
        val intent = Intent(activity, ProfileActivity::class.java)
        val args = Bundle()
        args.putString(ProfileFragment.PROFILE_KEY, username)
        intent.putExtra(ProfileActivity.USERNAME_KEY, args)
        startActivity(intent)
    }
}