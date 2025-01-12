package com.example.relationshipapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.example.relationshipapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView locationDetails, address;
    private SearchView searchBox;
    private Button filterButton;
    private Button saveReviewButton;
    private EditText reviewEditText;
    private RatingBar ratingBar;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Location> locations = new ArrayList<>();  // Unified list for all location types
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LatLng userLatLng; // Variable to store user's current location

    private boolean[] selectedFilters = new boolean[12];  // Array to store selected filter states
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);  // Ensure layout matches

        // Initialize UI elements
        mapView = findViewById(R.id.mapView);
        locationDetails = findViewById(R.id.locationDetails);
        address = findViewById(R.id.address);
        searchBox = findViewById(R.id.searchBox);
        filterButton = findViewById(R.id.filterButton);
        saveReviewButton = findViewById(R.id.saveReviewButton);
        reviewEditText = findViewById(R.id.reviewEditText);
        ratingBar = findViewById(R.id.ratingBar);

        // Initialize the FusedLocationProviderClient to get the user's location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize the MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Fetch locations from SQLite
        loadLocationsFromDatabase();

        // Filter Button click listener
        filterButton.setOnClickListener(v -> showFilterDialog());

        // Save Review Button click listener
        saveReviewButton.setOnClickListener(v -> saveReview());

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

    private void loadLocationsFromDatabase() {
        Cursor cursor = databaseHelper.getAllLocations();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Ensure the column index is valid before using it
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION_NAME);
                int latitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE);
                int typeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TYPE);
                int openHoursIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_OPEN_HOURS);
                int closeHoursIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLOSE_HOURS);
                int priceRangeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE_RANGE);
                int isPopularIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_POPULAR);

                // Check if any column index is -1
                if (nameIndex == -1 || latitudeIndex == -1 || longitudeIndex == -1 ||
                        typeIndex == -1 || openHoursIndex == -1 || closeHoursIndex == -1 ||
                        priceRangeIndex == -1 || isPopularIndex == -1) {
                    // Log the error or handle it as needed
                    Log.e("Database Error", "Column index is invalid. Check your database schema.");
                    return;  // Or handle this error accordingly
                }

                String name = cursor.getString(nameIndex);
                double latitude = cursor.getDouble(latitudeIndex);
                double longitude = cursor.getDouble(longitudeIndex);
                String type = cursor.getString(typeIndex);
                String openHours = cursor.getString(openHoursIndex);
                String closeHours = cursor.getString(closeHoursIndex);
                String priceRange = cursor.getString(priceRangeIndex);
                boolean isPopular = cursor.getInt(isPopularIndex) == 1;

                // Create Location object and add to the list
                locations.add(new Location(name, new LatLng(latitude, longitude), type, openHours, closeHours, priceRange, isPopular));

            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(10.8231, 106.6297), 12f)); // Ho Chi Minh City center

        // Initially show all markers (restaurants, cafes, hotels)
        showAllMarkers();

        // Set an OnMarkerClickListener to update location details when a marker is clicked
        googleMap.setOnMarkerClickListener(marker -> {
            updateLocationDetails(marker);  // Update details based on the clicked marker
            return true;  // Ensures that the event is consumed
        });

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

    private void showFilterDialog() {
        // Create an AlertDialog for filter options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Filters");

        // Define filter options (match these with the checkboxes' ids)
        String[] filters = {"Restaurant", "Cafe", "Hotel", "Low Price", "Medium Price", "High Price", "Open Now", "Any Time", "Nearby", "Farther", "Popular", "Not Popular"};

        // Set the initial checked states from the selectedFilters array
        builder.setMultiChoiceItems(filters, selectedFilters, (dialog, which, isChecked) -> {
            selectedFilters[which] = isChecked;  // Store the checked state in the array
        });

        // Add buttons to confirm or cancel
        builder.setPositiveButton("Apply", (dialog, which) -> applySelectedFilters());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void applySelectedFilters() {
        googleMap.clear();  // Clear existing markers

        for (Location location : locations) {
            boolean showLocation = true;

            // Check the selected filters and apply them
            if (selectedFilters[0] && !location.getType().equals("restaurant")) showLocation = false;  // Restaurant filter
            if (selectedFilters[1] && !location.getType().equals("cafe")) showLocation = false;  // Cafe filter
            if (selectedFilters[2] && !location.getType().equals("hotel")) showLocation = false;  // Hotel filter
            if (selectedFilters[3] && !location.getPriceRange().equals("low")) showLocation = false;  // Low price filter
            if (selectedFilters[4] && !location.getPriceRange().equals("medium")) showLocation = false;  // Medium price filter
            if (selectedFilters[5] && !location.getPriceRange().equals("high")) showLocation = false;  // High price filter
            if (selectedFilters[6] && !location.isOpenNow()) showLocation = false;  // Open Now filter
            if (selectedFilters[7] && location.isOpenNow()) showLocation = false;  // Any time filter (example)
            if (selectedFilters[8] && !location.isNearby(userLatLng, 5)) showLocation = false;  // Nearby filter (custom logic needed)
            if (selectedFilters[9] && location.isNearby(userLatLng, 5)) showLocation = false;  // Farther filter (custom logic needed)
            if (selectedFilters[10] && !location.isPopular()) showLocation = false;  // Popular filter
            if (selectedFilters[11] && location.isPopular()) showLocation = false;  // Not popular filter

            // If the location matches all selected filters, show it on the map
            if (showLocation) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);  // Tag the marker with the location data
            }
        }
    }

    private void updateLocationDetails(Marker marker) {
        Location location = (Location) marker.getTag();  // Get the location details from the marker's tag
        locationDetails.setText("Location: " + marker.getTitle());
        address.setText("Address: lat/lng: (" + marker.getPosition().latitude + ", " + marker.getPosition().longitude + ")");
    }

    private void saveReview() {
        // Get the review and rating from the UI
        String reviewText = reviewEditText.getText().toString();
        float rating = ratingBar.getRating();

        // If the review text is not empty, insert the review into the database
        if (!reviewText.isEmpty()) {
            String locationName = locationDetails.getText().toString(); // Use location name for filtering reviews
            Cursor cursor = databaseHelper.getAllLocations();

            int locationId = -1;
            while (cursor != null && cursor.moveToNext()) {
                // Ensure column indices are valid
                int locationNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION_NAME);
                int locationIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION_ID);

                // Check if either of the indices is invalid (should not be -1)
                if (locationNameIndex == -1 || locationIdIndex == -1) {
                    // Handle error if columns do not exist
                    Log.e("Database Error", "Required columns not found.");
                    break;
                }

                // If the location matches the one in the details, get its ID
                if (cursor.getString(locationNameIndex).equals(locationName)) {
                    locationId = cursor.getInt(locationIdIndex);
                    break;
                }
            }

            // Close the cursor to release resources
            if (cursor != null) {
                cursor.close();
            }

            // If LocationId is found, insert the review
            if (locationId != -1) {
                // Insert the review for the location
                databaseHelper.insertReview(locationId, rating, reviewText);
                Toast.makeText(this, "Review saved", Toast.LENGTH_SHORT).show();
            } else {
                // If no matching location is found
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If review text is empty, prompt the user
            Toast.makeText(this, "Please enter a review", Toast.LENGTH_SHORT).show();
        }
    }


    private void searchPlace(String query) {
        googleMap.clear();  // Clear existing markers
        for (Location location : locations) {
            if (location.getName().toLowerCase().contains(query.toLowerCase())) {
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location.getLatLng()).title(location.getName()));
                marker.setTag(location);
            }
        }
    }

    private void getUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
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
