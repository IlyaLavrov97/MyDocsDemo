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
import androidx.lifecycle.ViewModelProvider
import com.example.mydocdemo.R
import com.example.mydocdemo.viewmodel.document.DocumentViewModel
import com.example.mydocdemo.viewmodel.document.DocumentViewModelFactory
import kotlinx.android.synthetic.main.fragment_document_controller.*

class DocumentControllerFragment: Fragment() {
    private val viewModel: DocumentViewModel by activityViewModels(
        factoryProducer = { DocumentViewModelFactory() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_document_controller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        boldControllerButton.setOnClickListener {
            it.isSelected = !it.isSelected
            viewModel.setBold(it.isSelected)
        }

        italicControllerButton.setOnClickListener {
            it.isSelected = !it.isSelected
            viewModel.setItalic(it.isSelected)
        }

        sizeUpControllerButton.setOnClickListener {
            viewModel.plusSize()
        }

        sizeDownControllerButton.setOnClickListener {
            viewModel.minusSize()
        }

        viewModel.getSize().observe(viewLifecycleOwner, Observer {
            sizeControllerTextView.text = it.toString()
        })
    }
}