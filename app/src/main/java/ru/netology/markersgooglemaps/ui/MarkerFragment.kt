package ru.netology.markersgooglemaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.markersgooglemaps.databinding.FragmentMarkerBinding
import ru.netology.markersgooglemaps.dto.Marker

class MarkerFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMarkerBinding.inflate(
            inflater,
            container,
            false
        )

        val markerForView = arguments?.getParcelable<Marker>("marker") as Marker

        binding.apply {
            "Created on: ${markerForView.publishedDate}".also { markerFragmentCreated.text = it }
            etMarkerTitle.setText(markerForView.markerTitle)
            etMarkerDescription.setText(markerForView.markerDescription)

        }

        binding.mbMarkerExit.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}
