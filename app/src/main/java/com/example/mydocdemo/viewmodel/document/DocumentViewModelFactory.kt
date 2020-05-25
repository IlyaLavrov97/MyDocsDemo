package com.example.mydocdemo.viewmodel.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mydocdemo.interactor.document.GetDocumentInteractor
import com.example.mydocdemo.repository.document.FakeDocumentRepository

class DocumentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
            return DocumentViewModel(
                GetDocumentInteractor(FakeDocumentRepository())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}