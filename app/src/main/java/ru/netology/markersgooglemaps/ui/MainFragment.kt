package ru.netology.markersgooglemaps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.markersgooglemaps.adapter.MarkersAdapter
import ru.netology.markersgooglemaps.adapter.OnItemClickListener
import ru.netology.markersgooglemaps.databinding.FragmentMainBinding
import ru.netology.markersgooglemaps.dto.Marker
import ru.netology.markersgooglemaps.viewModel.MarkerViewModel

class MainFragment : Fragment() {

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        val adapter = MarkersAdapter(object : OnItemClickListener {

            override fun onMarker(marker: Marker) {

           }

           override fun onEdit(marker: Marker) {

           }

           override fun onDelete(marker: Marker) {

           }

        })

        binding.fabAddNewMarker.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_MapFragment)
        }

        return binding.root
    }
}