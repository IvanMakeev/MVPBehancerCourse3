package com.example.coursera_31_behancer_kotlin.ui.projects.user_projects

import com.arellomobile.mvp.InjectViewState
import com.example.coursera_31_behancer_kotlin.common.BasePresenter
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class UserProjectsPresenter(private val storage: Storage) : BasePresenter<UserProjectsView>() {

    fun getUserProjects(username: String) {
        compositeDisposable.add(ApiUtils.getApiService().getUserProjects(username)
            .doOnSuccess { response -> storage.insertProjects(response) }
            .onErrorReturn { throwable ->
                if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable::class.java))
                    storage.getProjects()
                else
                    null
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
                }
            )
        )
    }
}