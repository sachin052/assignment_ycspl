package com.example.assignment.feature.add_marker.presentation.views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.assignment.*
import com.example.assignment.core.extensions.getCenterPosition
import com.example.assignment.core.extensions.moveCameraAndAddMarkerTo
import com.example.assignment.core.extensions.toLatLong
import com.example.assignment.core.location.CustomFusedLocationProvider
import com.example.assignment.core.location.LocationProvider
import com.example.assignment.databinding.ActivityMapsBinding
import com.example.assignment.feature.add_marker.presentation.MapActivityCallBack
import com.example.assignment.feature.add_marker.presentation.MapViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationProvider.Listener,
    MapActivityCallBack {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var customFusedLocationProvider: CustomFusedLocationProvider
    private val mapViewModel: MapViewModel by viewModels()
    private val bottomSheetDialog by lazy { AddMarkerBottomSheetFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater).apply {
            setContentView(root)
            fabAdd.setOnClickListener {
                showBottomSheetDialog()
            }
            vm = mapViewModel
            lifecycleOwner = this@MapsActivity
            executePendingBindings()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        customFusedLocationProvider = CustomFusedLocationProvider(this)
        // check permissions;
        checkOrRequestPermissions()
        lifecycleScope.launch {
            bindObservers()
        }
        mapViewModel.setUpCallBack(this)
    }

    private suspend fun bindObservers() {

        with(mapViewModel) {
            addedMarkerLocation.collect { location ->
                location?.let {
                    if (this@MapsActivity::mMap.isInitialized)
                        mMap moveCameraAndAddMarkerTo location
                } ?: run {
                    if (this@MapsActivity::mMap.isInitialized)
                        mMap.clear()
                    if (bottomSheetDialog.isAdded && bottomSheetDialog.isVisible)
                        bottomSheetDialog.dismiss()
                }
            }
        }

    }

    private fun loadMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun checkOrRequestPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    customFusedLocationProvider.startUpdates()
                    loadMap()
                    customFusedLocationProvider.setListener(this)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        with(mMap) {
            setOnMapClickListener { location ->
                mMap.clear()
                mMap moveCameraAndAddMarkerTo location
                mapViewModel.addMarker(location)
            }
            setOnCameraMoveListener {
                clear()
                mapViewModel.addMarker(mMap.getCenterPosition())
            }
        }

    }

    override fun onNewLocationUpdate(location: Location) {
        mMap moveCameraAndAddMarkerTo location.toLatLong()
        updateLocationAndAddMarker(location)
    }

    private fun updateLocationAndAddMarker(location: Location) {
        with(mapViewModel) {
            addMarker(mMap.getCenterPosition())
            updateCurrentLocation(location)
        }
    }

    override fun onLocationUpdate(location: Location?) {
        location?.let {
            mMap moveCameraAndAddMarkerTo location.toLatLong()
            updateLocationAndAddMarker(location)
        }
    }

    private fun showBottomSheetDialog() {
        bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.javaClass.name)
    }

    override fun onClickLocationButton(location: Location?) {
        mMap moveCameraAndAddMarkerTo location?.toLatLong()
    }

}