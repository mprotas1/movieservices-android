package com.movieapp.android.model;

import java.util.UUID;

public class Cinema {
    private UUID id;
    private String name;
    private String address;

    private LocationPosition location;

    public Cinema(UUID id, String name, String address, LocationPosition location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationPosition getLocation() {
        return location;
    }

    public void setLocation(LocationPosition location) {
        this.location = location;
    }

}
