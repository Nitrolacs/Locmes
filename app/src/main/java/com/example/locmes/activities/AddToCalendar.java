package com.example.locmes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.locmes.R;

public class AddToCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_calendar);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(view -> onBackPressed());
    }
}