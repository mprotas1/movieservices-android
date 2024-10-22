package com.movieapp.android.service;

import com.movieapp.android.model.LocationPosition;

public class HaversineDistanceCalculator {
    private static final double EARTH_RADIUS = 6371.0d;

    /**
     * Calculates the distance between two locations using the Haversine formula. The result is returned in kilometers.
     *
     * @param currentPosition the current location
     * @param destination the destination location
     * @return the distance between the two locations in kilometers
     */
    public static double calculate(LocationPosition currentPosition, LocationPosition destination) {
        double latitudeDifference = Math.toRadians(destination.getLatitude() - currentPosition.getLatitude());
        double longitudeDifference = Math.toRadians(destination.getLongitude() - currentPosition.getLongitude());

        double currentLatitudePhi = Math.toRadians(currentPosition.getLatitude());
        double destinationLatitudePhi = Math.toRadians(destination.getLatitude());

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.cos(currentLatitudePhi) * Math.cos(destinationLatitudePhi) * Math.pow(Math.sin(longitudeDifference / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
