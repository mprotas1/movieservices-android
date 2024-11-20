package com.movieapp.android.model;

public class LocationPosition {
    private final double latitude;
    private final double longitude;

    public LocationPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
