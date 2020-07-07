package com.example.myapplication.model

sealed class LoadState {
    data class Error(val throwable: Throwable) : LoadState()
    object Loading : LoadState()
    object NotLoading : LoadState()
}