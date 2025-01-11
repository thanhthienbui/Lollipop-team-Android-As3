package com.example.relationshipapp;  // Adjust this to your actual package name

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.example.relationshipapp.Location;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView locationDetails, address;
    private SearchView searchBox;
    private RadioGroup venueTypeGroup;
    private FusedLocationProviderClient fusedLocationClient;



    private List<Location> locations = new ArrayList<>();  // Unified list for all location types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);  // Ensure layout matches

        // Initialize UI elements
        mapView = findViewById(R.id.mapView);
        locationDetails = findViewById(R.id.locationDetails);
        address = findViewById(R.id.address);
        searchBox = findViewById(R.id.searchBox);
        venueTypeGroup = findViewById(R.id.venueTypeGroup);

        // Initialize the FusedLocationProviderClient to get the user's location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize the MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Initialize locations
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

        // Search functionality (search by name of location)
        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPlace(query);  // Search for the location
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchPlace(newText);  // Search as the user types
                return false;
            }
        });
    }

    private void initLocations() {
        // Add locations dynamically with type information
        locations.add(new Location("Baozi Restaurant", new LatLng(10.7801, 106.6959), "restaurant"));
        locations.add(new Location("The Deck Saigon", new LatLng(10.7511, 106.7099), "restaurant"));
        locations.add(new Location("The Coffee House", new LatLng(10.7769, 106.6954), "cafe"));
        locations.add(new Location("Starbucks", new LatLng(10.7612, 106.6909), "cafe"));
        locations.add(new Location("Hotel Nikko Saigon", new LatLng(10.7771, 106.6937), "hotel"));
        locations.add(new Location("InterContinental Saigon", new LatLng(10.7762, 106.6995), "hotel"));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.8231, 106.6297), 12f)); // Ho Chi Minh City center

        // Initially show all markers (restaurants, cafes, hotels)
        showAllMarkers();

        // Automatically show details for the first marker (restaurant)
        if (!locations.isEmpty()) {
            Marker firstMarker = googleMap.addMarker(new MarkerOptions().position(locations.get(0).getLatLng()).title(locations.get(0).getName()));
            firstMarker.setTag(locations.get(0));  // Tag the marker with the location object
            updateLocationDetails(firstMarker);  // Update details based on the first marker
        }

        // Get the user's location and zoom the map
        getUserLocation();
    }

    private void showAllMarkers() {
        // Show all markers by default
        for (Location location : locations) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
            marker.setTag(location);  // Tag the marker with the location data
        }
    }

    private void filterMarkers(String type) {
        googleMap.clear();  // Clear existing markers
        for (Location location : locations) {
            if (location.getType().equals(type)) {
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
    }

    // Search functionality (search by name)
    private void searchPlace(String query) {
        googleMap.clear();  // Clear existing markers
        for (Location location : locations) {
            if (location.getName().toLowerCase().contains(query.toLowerCase())) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);  // Tag the marker with the location data
            }
        }
    }

    // Get the user's current location and zoom the map
    private void getUserLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));  // Zoom into user location
                        googleMap.addMarker(new MarkerOptions().position(userLatLng).title("You are here"));
                    } else {
                        Toast.makeText(MapActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                });
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
