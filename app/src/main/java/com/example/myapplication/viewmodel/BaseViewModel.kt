package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    protected fun Disposable.disposeOnClear(): Disposable {
        compositeDisposable.add(this)
        return this
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}