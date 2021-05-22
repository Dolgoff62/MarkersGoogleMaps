package ru.netology.markersgooglemaps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
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

        binding.swiperefresh.setOnRefreshListener {
            viewModel.loadMarkers()
        }

        val adapter = MarkersAdapter(object : OnItemClickListener {

            override fun onMarker(marker: Marker) {

            }

            override fun onEdit(marker: Marker) {

            }

            override fun onDelete(marker: Marker) {

            }

        })

        binding.rvMarkers.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )

        binding.rvMarkers.adapter = adapter
        viewModel.dataState.observe(viewLifecycleOwner, { state ->
            binding.swiperefresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                    .setAction(R.string.retry_loading) { viewModel.loadMarkers() }
                    .show()
            }
        })

        viewModel.data.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state.markers)
            binding.emptyList.isVisible = state.empty
        })

        binding.fabAddNewMarker.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_MapFragment)
        }

        return binding.root
    }
}