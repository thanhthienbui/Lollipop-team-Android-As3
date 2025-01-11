package com.example.relationshipapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.relationshipapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView locationDetails, address, rating;
    private EditText searchBox;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);  // Make sure the layout name matches

        // Initialize UI elements
        mapView = findViewById(R.id.mapView);
        locationDetails = findViewById(R.id.locationDetails);
        address = findViewById(R.id.address);
        rating = findViewById(R.id.rating);
        searchBox = findViewById(R.id.searchBox);
        backButton = findViewById(R.id.backButton);

        // Set up the MapView
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Back button listener
        backButton.setOnClickListener(v -> finish());

        // Set up the search box (this can be extended for filter functionality)
        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBox.getText().toString();
            // Implement search functionality here based on user input
            return false;
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Sample venue locations (replace with real data)
        LatLng venue1 = new LatLng(10.7801, 106.6959); // Example: Baozi Restaurant
        LatLng venue2 = new LatLng(10.7625, 106.6903); // Example: Cafe Milano

        googleMap.addMarker(new MarkerOptions().position(venue1).title("Baozi Restaurant"));
        googleMap.addMarker(new MarkerOptions().position(venue2).title("Cafe Milano"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(venue1, 15f)); // Zoom to first venue

        // Marker click listener to show venue details
        googleMap.setOnMarkerClickListener(marker -> {
            locationDetails.setText(marker.getTitle());
            address.setText("Sample Address"); // You can update this with actual data
            rating.setText("Rating: ★★★★☆"); // Update with real rating
            return false; // Don't consume the event
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
