package com.example.coursera_31_behancer_kotlin.ui.projects.all_projects

import com.arellomobile.mvp.InjectViewState
import com.example.coursera_31_behancer_kotlin.BuildConfig
import com.example.coursera_31_behancer_kotlin.common.BasePresenter
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class ProjectsPresenter(private val view: ProjectsView, private val storage: Storage) : BasePresenter<ProjectsView>() {

    fun getProjects() {
        compositeDisposable.add(ApiUtils.getApiService().getProjects(BuildConfig.API_QUERY)
            .doOnSuccess { response -> storage.insertProjects(response) }
            .onErrorReturn { throwable ->
                if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable::class.java)) {
                    storage.getProjects()
                } else {
                    null
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showRefresh() }
            .doFinally { viewState.hideRefresh() }
            .subscribe(
                { response ->
                    viewState.showProjects(response.projects)
                },
                {
                    viewState.showError()
                })
        )
    }

    fun openProfileFragment(username: String) {
        viewState.openProfileFragment(username)
    }
}