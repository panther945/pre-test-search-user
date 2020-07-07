package com.example.myapplication.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.HEADER_LINK
import com.example.myapplication.model.LoadState
import com.example.myapplication.model.User
import com.example.myapplication.parseNextLink
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class SearchUserDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val gitHubService: GitHubService,
    private val name: String
) : PageKeyedDataSource<String, User>() {
    val loadState = MutableLiveData<LoadState>()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, User>
    ) {
        if (name.isBlank()) {
            callback.onResult(emptyList(), null, null)
        } else {
            compositeDisposable.add(gitHubService.searchUsers(name)
                .map {
                    val nextUrl = it.headers().get(HEADER_LINK)?.parseNextLink()
                    if (it.isSuccessful) {
                        Pair(it.body()?.users, nextUrl)
                    } else {
                        throw HttpException(it)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadState.postValue(LoadState.Loading)
                }
                .subscribe({
                    val (users, nextPageUrl) = it
                    callback.onResult(users ?: emptyList(), null, nextPageUrl)
                    loadState.postValue(LoadState.NotLoading)
                }, { error ->
                    loadState.postValue(LoadState.Error(error))
                })
            )
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, User>) {
        compositeDisposable.add(gitHubService.searchUsersByUrl(params.key)
            .map {
                val nextUrl = it.headers().get(HEADER_LINK)?.parseNextLink()
                if (it.isSuccessful) {
                    Pair(it.body()?.users, nextUrl)
                } else {
                    throw HttpException(it)
                }
            }
            .onErrorResumeNext { Single.error(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loadState.postValue(LoadState.Loading)
            }
            .subscribe({
                val (users, nextPageUrl) = it
                callback.onResult(users ?: emptyList(), nextPageUrl)
                loadState.postValue(LoadState.NotLoading)
            }, { error ->
                loadState.postValue(LoadState.Error(error))
            })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, User>) {
    }
}