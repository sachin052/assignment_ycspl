package com.example.assignment.feature.add_marker.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.core.helpers.Either
import com.example.assignment.feature.add_marker.domain.ui_entity.PropertyUIEntity
import com.example.assignment.feature.add_marker.domain.use_cases.AddPropertyUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val addPropertyUseCase: AddPropertyUseCase) :
    ViewModel() {

    private val _addedMarkerLocation = MutableStateFlow<LatLng?>(null)
    val addedMarkerLocation = _addedMarkerLocation.asStateFlow()

    val addedPropertyName = MutableStateFlow<String?>(null)

    val addMarker: (LatLng?) -> Unit = { location ->
        viewModelScope.launch {
            _addedMarkerLocation.emit(location)
        }
    }

    // use location
    private val _userCurrentLocation = MutableStateFlow<Location?>(null)
    val updateCurrentLocation: (Location) -> Unit = { location ->
        viewModelScope.launch {
            _userCurrentLocation.emit(location)
        }
    }

    private lateinit var mapActivityCallBack:MapActivityCallBack

    fun setUpCallBack(mapActivityCallBack:MapActivityCallBack){
        this.mapActivityCallBack=mapActivityCallBack
    }


    fun addProperty() {
        if (_addedMarkerLocation.value == null) {
            // here can show the error
            return
        } else if (addedPropertyName.value.isNullOrEmpty()) {
            // here can show the error
            return
        } else {
            val entity = PropertyUIEntity(addedPropertyName.value!!, _addedMarkerLocation.value!!)
            viewModelScope.launch {
                addPropertyUseCase(entity).collect {
                    when (it) {
                        is Either.Left -> {}
                        is Either.Right -> {
                            clearAllData()
                        }
                    }
                }

            }
        }
    }

    fun clearAllData() {
        addMarker(null)
        viewModelScope.launch {
            addedPropertyName.emit(null)
        }
    }

    fun navigateToUserLocation() {
        mapActivityCallBack.onClickLocationButton(_userCurrentLocation.value)
    }
}

interface MapActivityCallBack{
    fun onClickLocationButton(location: Location?)
}

