package ru.netology.markersgooglemaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.netology.markersgooglemaps.databinding.DialogNewMarkerBinding
import ru.netology.markersgooglemaps.utils.Utils
import ru.netology.markersgooglemaps.viewModel.MarkerViewModel

class NewMarkerDialogFragment(
    private val externalLatitude: Double,
    private val externalLongitude: Double
) : DialogFragment() {

    companion object {
        const val TAG = "NewMarkerDialogFragment"
    }

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onStart() {
        super.onStart()
         dialog?.window?.setLayout(
             WindowManager.LayoutParams.MATCH_PARENT,
             WindowManager.LayoutParams.WRAP_CONTENT
         )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DialogNewMarkerBinding.inflate(
            inflater,
            container,
            false
        )

        binding.mbNewMarkerCancel.setOnClickListener {
            dismiss()
        }

        binding.mbNewMarkerConfirm.setOnClickListener {
            if (binding.etTitle.text.isNotEmpty()) {
                viewModel.run {
                    changeContent(
                        id = 0,
                        markerTitle = binding.etTitle.text.toString(),
                        markerDescription = binding.etDescription.text.toString(),
                        externalLatitude = externalLatitude,
                        externalLongitude = externalLongitude
                    )
                    saveMarker()
                    Utils.hideKeyboard(requireView())
                }
            }
            dismiss()
        }
        return binding.root
    }
}