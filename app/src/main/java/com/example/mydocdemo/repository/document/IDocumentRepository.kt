package com.example.mydocdemo.repository.document

import com.example.mydocdemo.entity.DocumentEntry
import io.reactivex.rxjava3.core.Single

interface IDocumentRepository {
    fun getDocument(): Single<DocumentEntry>
}