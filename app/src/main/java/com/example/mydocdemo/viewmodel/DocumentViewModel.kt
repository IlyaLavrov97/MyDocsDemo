package com.example.mydocdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mydocdemo.entity.DocumentEntry
import com.example.mydocdemo.interactor.document.IGetDocumentInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class DocumentViewModel (
    private val getDocumentInteractor: IGetDocumentInteractor
) : BaseViewModel() {
    private val document: MutableLiveData<DocumentEntry> = MutableLiveData()
    private val isBold: MutableLiveData<Boolean> = MutableLiveData()
    private val isItalic: MutableLiveData<Boolean> = MutableLiveData()
    private val isSizeUp: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getDocumentInteractor
            .getDocument()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { document.postValue(it) },
                onError = ::handleEx
            )
            .keepUntilDestroy()
    }

    fun getDocument() = document

    fun isBold() = isBold

    fun isItalic() = isItalic

    fun isSizeUp() = isSizeUp

    fun turnOnBold() {
        isBold.postValue(true)
    }

    fun turnOffBold() {
        isBold.postValue(false)
    }

    fun turnOnItalic() {
        isItalic.postValue(true)
    }

    fun turnOffItalic() {
        isItalic.postValue(false)
    }

    fun plusSize() {
        isSizeUp.postValue(true)
    }

    fun minusSize() {
        isSizeUp.postValue(false)
    }

    private fun handleEx(ex: Throwable) {
        // TODO impl handle
    }
}