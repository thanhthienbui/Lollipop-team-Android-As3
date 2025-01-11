package com.example.relationshipapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize the Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // Setup Search View
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Implement the search action (e.g., filter locations based on query)
                // For example, search for specific places by name
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Implement filtering as the user types (optional)
                // Update map markers or venue list based on search text
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Sample location for a dating venue
        LatLng venue = new LatLng(10.7769, 106.7009);  // Example coordinates for Baozi Restaurant
        mMap.addMarker(new MarkerOptions().position(venue).title("Baozi Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue, 14));

        // You can add more markers for different venues
        LatLng anotherVenue = new LatLng(10.7780, 106.7000); // Another venue location
        mMap.addMarker(new MarkerOptions().position(anotherVenue).title("Coffee Shop"));

        // Add more markers for other venues similarly
    }
}
