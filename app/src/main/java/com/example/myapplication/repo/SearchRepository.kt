package com.example.myapplication.repo

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.model.User
import io.reactivex.disposables.CompositeDisposable

const val PAGE_SIZE = 30

class SearchRepository(
    private val gitHubService: GitHubService
) {
    fun searchUsers(compositeDisposable: CompositeDisposable, name: String): Listing<User> {
        val sourceFactory = SearchUserDataSourceFactory(compositeDisposable, gitHubService, name)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        val livePagedList = LivePagedListBuilder(sourceFactory, config).build()
        return Listing(
            livePagedList,
            Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.loadState
            }
        )
    }
}