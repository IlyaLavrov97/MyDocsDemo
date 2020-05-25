package com.example.mydocdemo.repository.document

import com.example.mydocdemo.entity.DocumentEntry
import io.reactivex.rxjava3.core.Single

class FakeDocumentRepository : IDocumentRepository{
    override fun getDocument(): Single<DocumentEntry> {
        return Single.just(DocumentEntry("This"))
    }
}