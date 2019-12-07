package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class TripMapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;

    LatLng start_position;
    LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_map);

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        start_position = bundle.getParcelable("start_position");
        destination = bundle.getParcelable("destination");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.add(start_position);
        options.add(destination);
        Polyline polyline = mMap.addPolyline(options);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(start_position);
        builder.include(destination);
        LatLngBounds bounds = builder.build();
        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /**set animated zoom camera into map*/
                mMap.animateCamera(cu);
            }
        });

    }
}
