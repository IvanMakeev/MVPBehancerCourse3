package com.example.coursera_31_behancer_kotlin.ui.projects

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursera_31_behancer_kotlin.R
import com.example.coursera_31_behancer_kotlin.common.PresenterFragment
import com.example.coursera_31_behancer_kotlin.common.RefreshOwner
import com.example.coursera_31_behancer_kotlin.common.Refreshable
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.data.model.project.Project

abstract class BaseProjectsFragment : PresenterFragment(), BaseProjectsView, Refreshable {

    private lateinit var errorView: View
    private var refreshOwner: RefreshOwner? = null
    protected lateinit var recyclerView: RecyclerView
        private set
    protected var storage: Storage? = null
        private set
    protected var projectsAdapter: ProjectsAdapter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Storage.StorageOwner) storage = context.obtainStorage()

        if (context is RefreshOwner) refreshOwner = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler)
        errorView = view.findViewById(R.id.errorView)
    }

    override fun onDetach() {
        storage = null
        refreshOwner = null
        super.onDetach()
    }

    override fun showProjects(projects: List<Project>) {
        errorView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        projectsAdapter!!.addData(projects, true)
    }

    override fun showRefresh() {
        refreshOwner!!.setRefreshState(true)
    }

    override fun hideRefresh() {
        refreshOwner!!.setRefreshState(false)
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}