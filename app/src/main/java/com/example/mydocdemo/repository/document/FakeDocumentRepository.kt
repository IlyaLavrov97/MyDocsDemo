package com.example.mydocdemo.repository.document

import com.example.mydocdemo.entity.DocumentEntry
import io.reactivex.rxjava3.core.Single

class FakeDocumentRepository : IDocumentRepository{
    override fun getDocument(): Single<DocumentEntry> {
        return Single.just(DocumentEntry("Test g dkgdfg dfgdfkgh sfghs dfghdfs ghsdf kghsdf gh sf gsdfg sdjflghjsl dflgh sfjkgh sdfgsldfgh jfsdhg lfsdkhg dfslkjhg dsfljgs"))
    }
}