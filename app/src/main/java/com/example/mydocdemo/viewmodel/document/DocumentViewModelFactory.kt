package com.example.mydocdemo.viewmodel.document

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.mydocdemo.interactor.document.GetDocumentInteractor
import com.example.mydocdemo.repository.document.FakeDocumentRepository

class DocumentViewModelFactory(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
            return DocumentViewModel(
                GetDocumentInteractor(FakeDocumentRepository()),
                handle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}