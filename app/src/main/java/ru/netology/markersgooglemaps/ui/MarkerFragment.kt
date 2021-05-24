package ru.netology.markersgooglemaps.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.markersgooglemaps.R
import ru.netology.markersgooglemaps.databinding.FragmentMarkerBinding
import ru.netology.markersgooglemaps.dto.Marker
import ru.netology.markersgooglemaps.viewModel.MarkerViewModel

class MarkerFragment : Fragment() {

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

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
            markerMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_options)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuItemDelete -> {
                                viewModel.deleteMarker(markerForView.id)
                                findNavController().navigateUp()
                                true
                            }
                            R.id.menuItemEdit -> {
                                val bundle = Bundle().apply {
                                    putParcelable("marker", markerForView)
                                }
                                findNavController()
                                    .navigate(R.id.action_nav_marker_to_nav_edit, bundle)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            binding.mbMarkerExit.setOnClickListener {
                findNavController().navigateUp()
            }

            return binding.root
        }
    }
}
