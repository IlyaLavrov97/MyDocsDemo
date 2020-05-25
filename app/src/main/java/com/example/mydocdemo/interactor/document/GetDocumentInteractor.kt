package com.example.mydocdemo.interactor.document

import com.example.mydocdemo.entity.DocumentEntry
import com.example.mydocdemo.repository.document.IDocumentRepository
import io.reactivex.rxjava3.core.Single

class GetDocumentInteractor constructor(
    private val documentRepository: IDocumentRepository
) : IGetDocumentInteractor {
    override fun getDocument(): Single<DocumentEntry> {
        return documentRepository.getDocument()
    }
}