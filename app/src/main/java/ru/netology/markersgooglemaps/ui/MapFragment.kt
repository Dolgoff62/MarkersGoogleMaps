package ru.netology.markersgooglemaps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.collections.MarkerManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.utils.collection.addMarker
import ru.netology.markersgooglemaps.databinding.FragmentMapBinding
import ru.netology.markersgooglemaps.ui.NewMarkerDialogFragment
import ru.netology.markersgooglemaps.utils.Utils
import ru.netology.markersgooglemaps.viewModel.MarkerViewModel

class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }

    private val viewModel: MarkerViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                googleMap.apply {
                    isMyLocationEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.gps_not_allowed),
                    Toast.LENGTH_LONG
                ).show()
                return@registerForActivityResult
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = mapFragment.awaitMap().apply {
                isTrafficEnabled = false
                isBuildingsEnabled = true

                uiSettings.apply {
                    isZoomControlsEnabled = true
                    setAllGesturesEnabled(true)
                }
            }


            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    googleMap.apply {
                        this.isMyLocationEnabled = true
                        uiSettings.isMyLocationButtonEnabled = true
                    }

                    val fusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(requireActivity())

                    fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                        println(it)
                    }
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    //TODO
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            viewModel.data.observe(viewLifecycleOwner, { state ->
                if (state.markers.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_empty_marker_list,
                        Toast.LENGTH_LONG
                    ).show()
                }

                val markerManager = MarkerManager(googleMap)
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_google_maps_marker_24)

                val collection: MarkerManager.Collection = markerManager.newCollection().apply {
                    state.markers.forEach { marker ->
                        addMarker {
                            position(LatLng(marker.latitude, marker.longitude))
                            icon(icon)
                            title(marker.markerTitle)
                            snippet(marker.markerDescription)
                            infoWindowAnchor(0.5F, 0F)
                        }
                    }
                }

                collection.setOnMarkerClickListener { marker ->
                    marker.showInfoWindow()
                    true
                }

                val boundsBuilder = LatLngBounds.Builder()
                if (state.markers.isNotEmpty()) {
                    state.markers.forEach { marker ->
                        boundsBuilder.include(LatLng(marker.latitude, marker.longitude))
                    }
                    val bounds = boundsBuilder.build()
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
                }

                collection.setOnInfoWindowClickListener {
                    googleMap.moveCamera(
                        CameraUpdateFactory.newCameraPosition(
                            cameraPosition {
                                target(it.position)
                                zoom(15F)
                            }
                        )
                    )
                }
            })

            googleMap.setOnMapLongClickListener { point ->
                NewMarkerDialogFragment(point.latitude, point.longitude)
                    .show(childFragmentManager, NewMarkerDialogFragment.TAG)
            }

            val coordinatesString = arguments?.textArg?.split(",")?.toTypedArray()

            val coordinates = coordinatesString?.get(0)?.let {
                LatLng(it.toDouble(), coordinatesString[1].toDouble())
            }

            if (coordinates != null) {
                googleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                        cameraPosition {
                            target(coordinates)
                            zoom(15F)
                        }
                    )
                )
            }
        }
    }

    companion object {
        var Bundle.textArg: String? by Utils.StringArg
    }
}