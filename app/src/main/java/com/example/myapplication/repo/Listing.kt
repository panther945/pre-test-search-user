package com.example.myapplication.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.myapplication.model.LoadState

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val loadState: LiveData<LoadState>
)