package com.example.assignment.core

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.assignment.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("setLatLong")
fun TextInputEditText.setLatLong(latLng: LatLng?) {
    latLng?.let {
        setText(
            String.format(
                context.getString(R.string.lat_long),
                latLng.latitude.toString(),
                latLng.longitude.toString()
            )
        )
    }
}