package com.enterprise.agino.ui.addnewsensor


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
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.R
import com.enterprise.agino.databinding.FragmentAddNewSensorBinding
import com.enterprise.agino.ui.view.OnScrollMapViewWrapperTouchListener
import com.tomtom.quantity.Distance
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProviderConfig
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.style.*
import com.tomtom.sdk.map.display.ui.MapFragment
import kotlin.time.Duration.Companion.milliseconds

class AddNewSensorFragment : Fragment(), OnScrollMapViewWrapperTouchListener {
    private var _binding: FragmentAddNewSensorBinding? = null
    private val binding get() = _binding!!

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Toast.makeText(
                    context,
                    "Agino will default to coarse approximation of locations.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                Toast.makeText(
                    context,
                    "Agino was denied access to location",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewSensorBinding.inflate(inflater, container, false)

        setupMap()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            addNewSensorButton.setOnClickListener {
                findNavController().navigate(AddNewSensorFragmentDirections.actionAddNewSensorFragmentToHomeFragment())
            }
        }
    }

    private fun setupMap() {
        val mapOptions = MapOptions(
            mapKey = "QKG5sZdSQCFSitlPKDsIHZ9FV7ZGZg4g",
        )
        val mapFragment = MapFragment.newInstance(mapOptions)

        mapFragment.getMapAsync { tomtomMap: TomTomMap ->
            tomtomMap.loadStyle(StandardStyles.SATELLITE, object : StyleLoadingCallback {
                override fun onSuccess() {
                    // TODO: handle success
                }

                override fun onFailure(error: LoadingStyleFailure) {
                    // TODO: handle error
                }
            })

            setupLocationProvider(tomtomMap)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        binding.frameLayout.mListener = this
    }

    private fun setupLocationProvider(tomtomMap: TomTomMap) {
        val androidLocationProviderConfig = AndroidLocationProviderConfig(
            minTimeInterval = 250L.milliseconds,
            minDistance = Distance.meters(20.0)
        )
        val locationProvider: LocationProvider = AndroidLocationProvider(
            context = requireContext().applicationContext,
            config = androidLocationProviderConfig
        )

        tomtomMap.setLocationProvider(locationProvider)
        locationProvider.enable()

        val currentLocation: GeoLocation? = tomtomMap.currentLocation
        currentLocation?.let {
            tomtomMap.moveCamera(
                CameraOptions(
                    position = it.position,
                    zoom = 10.0,
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                //TODO: show request permission rationale
            }
            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTouch() {
        binding.scrollView.requestDisallowInterceptTouchEvent(true)
    }
}