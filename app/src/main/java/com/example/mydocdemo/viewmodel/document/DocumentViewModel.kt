package com.example.mydocdemo.viewmodel.document

import androidx.lifecycle.MutableLiveData
import com.example.mydocdemo.entity.DocumentEntry
import com.example.mydocdemo.interactor.document.IGetDocumentInteractor
import com.example.mydocdemo.viewmodel.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


class DocumentViewModel(
    getDocumentInteractor: IGetDocumentInteractor
) : BaseViewModel() {

    private val document: MutableLiveData<DocumentEntry> = MutableLiveData()
    private val isBold: MutableLiveData<Boolean> = MutableLiveData()
    private val isItalic: MutableLiveData<Boolean> = MutableLiveData()
    private val isSizeUp: MutableLiveData<Boolean> = MutableLiveData()
    private val size: MutableLiveData<Int> = MutableLiveData()

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

    fun getSize() = size

    fun setBold(value: Boolean) {
        isBold.postValue(value)
    }

    fun setItalic(value: Boolean) {
        isItalic.postValue(value)
    }

    fun plusSize() {
        isSizeUp.postValue(true)
    }

    fun minusSize() {
        isSizeUp.postValue(false)
    }

    fun postNewSize(newSize: Int) {
        size.postValue(newSize)
    }
}