package com.example.assignment.core.location

import android.location.Location

/**
 * Interface that is used for abstracting location updates from location providers.
 * <p>
 * A device should be checked to ensure it has location services enabled before getting a location.
 */
interface LocationProvider {
    /**
     * Subscribe to notifications for location updates
     * @param listener
     */
    fun setListener(listener: Listener?)

    /**
     * Gets the current location known to the [LocationProvider]
     */
    fun getCurrentLocation(): Location?

    /**
     * Starts the location provider's services
     */
    fun startUpdates()

    /**
     * Stops the location provider's services
     */
    fun stopUpdates()

    /**
     * Listener to receive updates from a [LocationProvider]
     */
    interface Listener {
        /**
         * Triggered when a new location is received
         */
        fun onNewLocationUpdate(location: Location)

        /**
         * Triggered when any location update is received
         */
        fun onLocationUpdate(location: Location?)
    }
}