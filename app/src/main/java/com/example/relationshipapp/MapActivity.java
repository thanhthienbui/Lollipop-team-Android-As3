package com.example.relationshipapp;  // Adjust this to your actual package name

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView locationDetails, address, rating;
    private SearchView searchBox;
    private RadioGroup venueTypeGroup;

    private List<Location> restaurants = new ArrayList<>();
    private List<Location> cafes = new ArrayList<>();
    private List<Location> hotels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);  // Ensure layout matches

        // Initialize UI elements
        mapView = findViewById(R.id.mapView);
        locationDetails = findViewById(R.id.locationDetails);
        address = findViewById(R.id.address);
        rating = findViewById(R.id.rating);
        searchBox = findViewById(R.id.searchBox);
        venueTypeGroup = findViewById(R.id.venueTypeGroup);

        // Initialize the MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Initialize locations for each category
        initLocations();

        // Venue Type Filter functionality
        venueTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Handle filter changes when user selects a venue type
            if (checkedId == R.id.restaurant) {
                filterMarkers("restaurant");
            } else if (checkedId == R.id.cafe) {
                filterMarkers("cafe");
            } else if (checkedId == R.id.hotel) {
                filterMarkers("hotel");
            }
        });
    }

    private void initLocations() {
        // Add restaurant locations
        restaurants.add(new Location("Baozi Restaurant", new LatLng(10.7801, 106.6959), "restaurant"));
        restaurants.add(new Location("The Deck Saigon", new LatLng(10.7511, 106.7099), "restaurant"));

        // Add cafe locations
        cafes.add(new Location("The Coffee House", new LatLng(10.7769, 106.6954), "cafe"));
        cafes.add(new Location("Starbucks", new LatLng(10.7612, 106.6909), "cafe"));

        // Add hotel locations
        hotels.add(new Location("Hotel Nikko Saigon", new LatLng(10.7771, 106.6937), "hotel"));
        hotels.add(new Location("InterContinental Saigon", new LatLng(10.7762, 106.6995), "hotel"));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.8231, 106.6297), 12f)); // Ho Chi Minh City center

        // Initially show all markers (restaurants, cafes, hotels)
        showAllMarkers();

        // Automatically show details for the first marker (restaurant)
        if (!restaurants.isEmpty()) {
            Marker firstMarker = googleMap.addMarker(new MarkerOptions().position(restaurants.get(0).getLatLng()).title(restaurants.get(0).getName()));
            firstMarker.setTag(restaurants.get(0));  // Tag the marker with the location object
            updateLocationDetails(firstMarker);  // Update details based on the first marker
        }
    }

    private void showAllMarkers() {
        // Show all markers by default
        for (Location location : restaurants) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
            marker.setTag(location);  // Tag the marker with the location data
        }
        for (Location location : cafes) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
            marker.setTag(location);  // Tag the marker with the location data
        }
        for (Location location : hotels) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
            marker.setTag(location);  // Tag the marker with the location data
        }
    }

    private void filterMarkers(String type) {
        googleMap.clear();  // Clear existing markers
        if ("restaurant".equals(type)) {
            for (Location location : restaurants) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);  // Tag the marker with the location data
            }
        } else if ("cafe".equals(type)) {
            for (Location location : cafes) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);  // Tag the marker with the location data
            }
        } else if ("hotel".equals(type)) {
            for (Location location : hotels) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);  // Tag the marker with the location data
            }
        }
    }

    // Method to update location details (called when a marker is clicked or initialized)
    private void updateLocationDetails(Marker marker) {
        Location location = (Location) marker.getTag();  // Get the location details from the marker's tag
        locationDetails.setText("Location: " + marker.getTitle());
        address.setText("Address: lat/lng: (" + marker.getPosition().latitude + ", " + marker.getPosition().longitude + ")");
        rating.setText("Rating: ★★★★☆");  // Example rating (replace with actual rating logic)
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
