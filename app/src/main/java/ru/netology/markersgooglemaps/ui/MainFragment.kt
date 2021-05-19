package ru.netology.markersgooglemaps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.netology.markersgooglemaps.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )

        binding.fabAddNewMarker.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_MapFragment)
        }

        return binding.root
    }
}