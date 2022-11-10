package com.example.assignment.core.extensions

import android.location.Location
import com.example.assignment.core.Constants.kMap_ZOOM_LEVEL
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

fun Location.toLatLong(): LatLng {
    return LatLng(latitude,longitude)
}

/**
 * This will move camera and marker to the center of the map
 */
infix fun GoogleMap.moveCameraAndAddMarkerTo(latLng: LatLng){
    moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, kMap_ZOOM_LEVEL))
    addMarker(
        MarkerOptions().position(cameraPosition.target).title("My Position")
    )
}

fun GoogleMap.getCenterPosition(): LatLng {
    return cameraPosition.target
}
