package com.enterprise.agino.ui.first_time.farm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enterprise.agino.R
import com.enterprise.agino.common.Constants
import com.enterprise.agino.common.Resource
import com.enterprise.agino.databinding.FragmentNewFarmBinding
import com.enterprise.agino.utils.afterTextChanged
import com.enterprise.agino.utils.showErrorSnackBar
import com.enterprise.agino.utils.showSuccessSnackBar
import com.tomtom.quantity.Distance
import com.tomtom.sdk.location.GeoLocation
import com.tomtom.sdk.location.LocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProvider
import com.tomtom.sdk.location.android.AndroidLocationProviderConfig
import com.tomtom.sdk.map.display.MapOptions
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.camera.CameraOptions
import com.tomtom.sdk.map.display.style.LoadingStyleFailure
import com.tomtom.sdk.map.display.style.StandardStyles
import com.tomtom.sdk.map.display.style.StyleLoadingCallback
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.search.SearchCallback
import com.tomtom.sdk.search.SearchOptions
import com.tomtom.sdk.search.SearchResponse
import com.tomtom.sdk.search.common.error.SearchFailure
import com.tomtom.sdk.search.online.OnlineSearch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


@AndroidEntryPoint
class NewFarmFragment : Fragment() {
    private var _binding: FragmentNewFarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: TomTomMap

    private val viewModel by viewModels<NewFarmViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFarmBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupMap()
        setupListeners()
        return binding.root
    }

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
                    context, "Agino was denied access to location", Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupListeners() {
        lifecycleScope.launchWhenCreated {
            launch {
                binding.locationInput.afterTextChanged {
                    if (it.isNotEmpty()) searchLocation(it)
                }
            }

            launch {
                viewModel.formSubmissionResult.collect {
                    if (it is Resource.Success) {
                        showSuccessSnackBar("Successfully added farm", binding.root)
                        findNavController().popBackStack()
                    } else if (it is Resource.Error) {
                        showErrorSnackBar("Error adding farm: ${it.message}", binding.root)
                    }
                }
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), R.layout.exposed_dropdown_item, arrayOf()
        )
        val textView = binding.locationInput
        textView.setAdapter(adapter)

        viewModel.searchResults.observe(viewLifecycleOwner) {
            (textView.adapter as ArrayAdapter<*>).clear()
            (textView.adapter as ArrayAdapter<String>).addAll(it.map { pair -> pair.first })
            (textView.adapter as ArrayAdapter<*>).notifyDataSetChanged()
            textView.showDropDown()

            textView.setOnItemClickListener { _, _, idx, _ ->
                textView.setText(viewModel.searchResults.value!![idx].first)
                map.moveCamera(options = CameraOptions(position = viewModel.searchResults.value!![idx].second))
            }
        }

    }

    private fun searchLocation(query: String) {
        val search = OnlineSearch.create(requireContext(), Constants.MAP_KEY)

        val searchOptions = SearchOptions(query = query, limit = 5)
        search.search(searchOptions, object : SearchCallback {
            override fun onSuccess(result: SearchResponse) {
                Log.i("TEXT Result", result.toString())
                viewModel.searchResults.value = result.results.map {
                    val fallbackName =
                        "${it.place.coordinate.latitude}, ${it.place.coordinate.longitude}"
                    Pair(
                        it.place.address?.freeformAddress ?: fallbackName, it.place.coordinate
                    )
                }
            }

            override fun onFailure(failure: SearchFailure) {
                Log.i("TEXT RESULT", "ERROR: ${failure.message}")
            }
        })
    }

    private fun setupMap() {
        val mapFragment = MapFragment.newInstance(
            mapOptions = MapOptions(
                mapKey = Constants.MAP_KEY,
            )
        )

        mapFragment.getMapAsync { tomtomMap: TomTomMap ->
            map = tomtomMap
            map.addCameraChangeListener {
                viewModel.farmAddress.value = map.cameraPosition.position
            }

            tomtomMap.loadStyle(StandardStyles.SATELLITE, object : StyleLoadingCallback {
                override fun onSuccess() {
                    // TODO: handle success
                }

                override fun onFailure(failure: LoadingStyleFailure) {
                    // TODO: handle error
                }
            })

            setupLocationProvider(tomtomMap)
        }

        parentFragmentManager.beginTransaction().replace(R.id.map_container, mapFragment).commit()
    }

    private fun setupLocationProvider(tomtomMap: TomTomMap) {
        val androidLocationProviderConfig = AndroidLocationProviderConfig(
            minTimeInterval = 250L.milliseconds, minDistance = Distance.meters(20.0)
        )
        val locationProvider: LocationProvider = AndroidLocationProvider(
            context = requireContext().applicationContext, config = androidLocationProviderConfig
        )

        tomtomMap.setLocationProvider(locationProvider)
        locationProvider.enable()

        val currentLocation: GeoLocation? = tomtomMap.currentLocation
        currentLocation?.let {
            tomtomMap.moveCamera(
                CameraOptions(
                    position = it.position,
                    zoom = 15.0,
                )
            )
        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLocationPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}