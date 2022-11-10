package com.example.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.assignment.databinding.AddMarkerLayoutBinding
import com.example.assignment.feature.add_marker.presentation.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddMarkerBottomSheetFragment:BottomSheetDialogFragment() {
    private lateinit var addMarkerLayoutBinding: AddMarkerLayoutBinding
    private val mapViewModel: MapViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addMarkerLayoutBinding = AddMarkerLayoutBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
            viewModel=mapViewModel
        }
        return addMarkerLayoutBinding.root
    }
}