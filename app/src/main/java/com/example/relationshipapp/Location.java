package com.example.relationshipapp;  // Ensure this matches your actual package

import com.google.android.gms.maps.model.LatLng;

public class Location {

    private String name;
    private LatLng latLng;
    private String type;
    private String openTime; // Opening time (e.g., "9:00 AM")
    private String closeTime; // Closing time (e.g., "10:00 PM")
    private String priceRange; // Price range (e.g., "low", "medium", "high")
    private boolean isPopular; // Whether the location is popular or not

    // Constructor with parameters for name, LatLng, type, operating hours, price range, and popularity
    public Location(String name, LatLng latLng, String type, String openTime, String closeTime, String priceRange, boolean isPopular) {
        this.name = name;
        this.latLng = latLng;
        this.type = type;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.priceRange = priceRange;
        this.isPopular = isPopular;
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

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public boolean isPopular() {
        return isPopular;
    }

    // Method to check if the location is currently open (based on the current time)
    public boolean isOpenNow() {
        // Get the current time
        long currentTimeMillis = System.currentTimeMillis();
        String currentTime = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date(currentTimeMillis));

        // Compare current time with open and close times
        return currentTime.compareTo(openTime) >= 0 && currentTime.compareTo(closeTime) <= 0;
    }

    public boolean isNearby(LatLng userLocation, double thresholdDistanceInKm) {
        // Example logic for calculating nearby distance (using Haversine formula or any other method)
        double distance = calculateDistance(this.latLng, userLocation);  // You need to implement this method
        return distance <= thresholdDistanceInKm;
    }

    public boolean isFarther(LatLng userLocation, double thresholdDistanceInKm) {
        double distance = calculateDistance(this.latLng, userLocation);  // You need to implement this method
        return distance > thresholdDistanceInKm;
    }

    // Helper method to calculate distance between two LatLng points (in km)
    private double calculateDistance(LatLng point1, LatLng point2) {
        double earthRadius = 6371; // Radius in kilometers
        double dLat = Math.toRadians(point2.latitude - point1.latitude);
        double dLon = Math.toRadians(point2.longitude - point1.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(point1.latitude)) * Math.cos(Math.toRadians(point2.latitude)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;  // Distance in kilometers
    }
}
