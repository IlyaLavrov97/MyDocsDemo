package com.example.mydocdemo.view.document

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import com.example.mydocdemo.R
import com.example.mydocdemo.viewmodel.DocumentViewModel
import kotlinx.android.synthetic.main.fragment_document_viewer.*

class DocumentViewerFragment: Fragment() {
    private val viewModel: DocumentViewModel by activityViewModels(
        factoryProducer = { SavedStateViewModelFactory(Application(), this) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDocument().observe(viewLifecycleOwner, Observer {
            viewerTextView.text = it.content
        })

        viewModel.isBold().observe(viewLifecycleOwner, Observer {
            viewerTextView.isBold = it
        })

        viewModel.isItalic().observe(viewLifecycleOwner, Observer {
            viewerTextView.isItalic = it
        })

        viewModel.isSizeUp().observe(viewLifecycleOwner, Observer {
            if (it) {
                viewerTextView.currentFontSize+=5
            } else {
                viewerTextView.currentFontSize-=5
            }

            viewModel.postNewSize(viewerTextView.currentFontSize.toInt())
        })

        viewModel.postNewSize(viewerTextView.currentFontSize.toInt())
    }
}