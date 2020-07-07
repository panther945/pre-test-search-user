package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.example.myapplication.model.LoadState
import com.example.myapplication.model.User
import com.example.myapplication.repo.SearchRepository
import com.example.myapplication.ui.debounce

class MainActivityViewModel(
    private val repo: SearchRepository
) : BaseViewModel() {
    val q = MutableLiveData<String>()
    private val searchResult = Transformations.map(q.debounce()) {
        repo.searchUsers(compositeDisposable, it)
    }
    val users: LiveData<PagedList<User>> = Transformations.switchMap(searchResult) {
        it.pagedList
    }
    val loadState: LiveData<LoadState> = Transformations.switchMap(searchResult) {
        it.loadState
    }
}