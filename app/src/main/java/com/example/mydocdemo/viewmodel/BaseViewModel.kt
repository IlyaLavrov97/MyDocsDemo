package com.example.mydocdemo.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private val destroyCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        destroyCompositeDisposable.clear()
    }

    protected fun handleEx(ex: Throwable) {
        // TODO impl handle
    }

    protected fun Disposable.keepUntilDestroy() {
        destroyCompositeDisposable.add(this)
    }
}