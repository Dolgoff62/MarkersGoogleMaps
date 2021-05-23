package ru.netology.markersgooglemaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.markersgooglemaps.databinding.FragmentEditMarkerBinding
import ru.netology.markersgooglemaps.dto.Marker
import ru.netology.markersgooglemaps.utils.Utils
import ru.netology.markersgooglemaps.viewModel.MarkerViewModel

class EditMarkerFragment() : Fragment() {

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentEditMarkerBinding.inflate(
            inflater,
            container,
            false
        )

        val markerForEdit = arguments?.getParcelable<Marker>("marker") as Marker

        binding.apply {
            etEditTitle.setText(markerForEdit.markerTitle)
            etEditDescription.setText(markerForEdit.markerDescription)
        }

        binding.mbEditMarkerCancel.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.mbEditMarkerConfirm.setOnClickListener {
            if (
                binding.etEditTitle.text.equals(markerForEdit.markerTitle) &&
                binding.etEditDescription.text.equals(markerForEdit.markerDescription)
            ) {
                findNavController().navigateUp()
            }

            viewModel.run {
                changeContent(
                    id = markerForEdit.id,
                    markerTitle = binding.etEditTitle.text.toString(),
                    markerDescription = binding.etEditDescription.text.toString(),
                    externalLatitude = markerForEdit.latitude,
                    externalLongitude = markerForEdit.longitude
                )
                saveMarker()
                Utils.hideKeyboard(requireView())
            }

            findNavController().navigateUp()
        }
        return binding.root
    }
}
