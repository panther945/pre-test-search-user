package com.example.myapplication.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> LiveData<T>.debounce(duration: Long = 250L) = MediatorLiveData<T>().also {
    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable {
        it.value = this.value
    }

    it.addSource(this) {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, duration)
    }
}