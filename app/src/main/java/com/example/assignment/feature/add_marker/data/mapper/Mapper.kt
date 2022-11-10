package com.example.assignment.feature.add_marker.data.mapper

import com.example.assignment.core.data_base.PropertyDataBaseEntity
import com.example.assignment.feature.add_marker.domain.ui_entity.PropertyUIEntity
import com.google.android.gms.maps.model.LatLng

object Mapper {
    fun toUIEntity(entity: PropertyDataBaseEntity): PropertyUIEntity {
        return PropertyUIEntity(
            propertyName = entity.propertyName,
            propertyLocation = LatLng(entity.lat, entity.lng)
        )
    }

    fun toDBEntity(entity: PropertyUIEntity):PropertyDataBaseEntity{
        return PropertyDataBaseEntity(lat = entity.propertyLocation.latitude, lng = entity.propertyLocation.longitude, propertyName = entity.propertyName)
    }
}
