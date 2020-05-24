package com.example.mydocdemo.interactor.document

import com.example.mydocdemo.entity.DocumentEntry
import io.reactivex.rxjava3.core.Single

interface IGetDocumentInteractor {
    fun getDocument(): Single<DocumentEntry>
}