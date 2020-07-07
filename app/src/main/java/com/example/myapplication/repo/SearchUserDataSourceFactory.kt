package com.example.myapplication.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.myapplication.model.User
import io.reactivex.disposables.CompositeDisposable


class SearchUserDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val gitHubService: GitHubService,
    private val name: String
) : DataSource.Factory<String, User>() {
    val sourceLiveData = MutableLiveData<SearchUserDataSource>()

    override fun create(): DataSource<String, User> {
        val source = SearchUserDataSource(compositeDisposable, gitHubService, name)
        sourceLiveData.postValue(source)
        return source
    }
}