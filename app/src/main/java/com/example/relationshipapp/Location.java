package com.example.relationshipapp;  // Ensure this matches your actual package

import com.google.android.gms.maps.model.LatLng;

public class Location {

    private String name;
    private LatLng latLng;
    private String type;

    // Constructor with parameters String (name), LatLng (latLng), and String (type)
    public Location(String name, LatLng latLng, String type) {
        this.name = name;
        this.latLng = latLng;
        this.type = type;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getType() {
        return type;
    }
}
