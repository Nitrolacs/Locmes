package com.example.locmes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.locmes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddToMap extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(55.994336798773816, 92.79748762057362);
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Новая заметка"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }
}