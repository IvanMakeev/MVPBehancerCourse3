package com.example.coursera_31_behancer_kotlin.ui.profile

import com.arellomobile.mvp.InjectViewState
import com.example.coursera_31_behancer_kotlin.common.BasePresenter
import com.example.coursera_31_behancer_kotlin.data.Storage
import com.example.coursera_31_behancer_kotlin.utils.ApiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@InjectViewState
class ProfilePresenter(private val view: ProfileView, private val storage: Storage) : BasePresenter<ProfileView>() {

    fun getProfile(username: String) {
        compositeDisposable.add(ApiUtils.getApiService().getUserInfo(username)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { response -> storage.insertUser(response) }
            .onErrorReturn { throwable ->
                if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable::class.java)) {
                    storage.getUser(username)
                } else {
                    null
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showRefresh() }
            .doFinally { viewState.hideRefresh() }
            .subscribe(
                { response ->
                    viewState.showProfile()
                    viewState.bind(response.user)
                },
                {
                    viewState.showError()
                })
        )
    }
}