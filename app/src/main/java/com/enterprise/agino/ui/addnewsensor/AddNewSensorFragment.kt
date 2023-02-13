package com.enterprise.agino.ui.addnewsensor


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.enterprise.agino.databinding.FragmentAddNewSensorBinding
import com.enterprise.agino.ui.view.OnScrollMapViewWrapperTouchListener
import com.enterprise.agino.utils.showErrorSnackBar
import com.enterprise.agino.utils.showSuccessSnackBar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
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
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class AddNewSensorFragment : Fragment(), OnScrollMapViewWrapperTouchListener {
    private var _binding: FragmentAddNewSensorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNewSensorViewModel by viewModels()

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
        _binding = FragmentAddNewSensorBinding.inflate(
            inflater,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupMap()
        setupFlowListeners()
        setupCalendarInputs()
        return binding.root
    }

    private fun setupFlowListeners() {
        lifecycleScope.launchWhenCreated {
            viewModel.formSubmissionResult.collect { formResult ->
                if (formResult is Resource.Success) {
                    showSuccessSnackBar("Successfully added sensor", binding.root)
                    findNavController().popBackStack()
                } else if (formResult is Resource.Error) {
                    showErrorSnackBar(
                        "Error while adding sensor: ${formResult.message}",
                        binding.root
                    )
                }
            }
        }
    }

    private fun setupCalendarInputs() {
        binding.apply {
            installationDateInput.setOnClickListener {
                showInstallationDatePicker()
            }

            lastCuttingDateInput.setOnClickListener {
                showLastCuttingDatePicker()
            }

            installationDateInput.inputType = InputType.TYPE_NULL
            lastCuttingDateInput.inputType = InputType.TYPE_NULL
        }
    }

    private fun showLastCuttingDatePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now()).build()

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            viewModel.lastFieldCuttingDate.value = Date(it)
        }

        datePicker.show(requireActivity().supportFragmentManager, null)
    }

    private fun showInstallationDatePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now()).build()

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder)
                .build()

        datePicker.addOnPositiveButtonClickListener {
            viewModel.sensorInstallationDate.value = Date(it)
        }

        datePicker.show(requireActivity().supportFragmentManager, null)
    }

    private fun setupMap() {
        val mapOptions = MapOptions(
            mapKey = Constants.MAP_KEY,
        )
        val mapFragment = MapFragment.newInstance(mapOptions)

        mapFragment.getMapAsync { tomtomMap: TomTomMap ->
            tomtomMap.addCameraChangeListener {
                viewModel.location.value = tomtomMap.cameraPosition.position
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
                    zoom = 15.0,
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