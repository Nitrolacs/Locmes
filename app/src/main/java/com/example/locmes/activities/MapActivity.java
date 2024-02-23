package com.example.locmes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.locmes.R;
import com.google.android.material.navigation.NavigationView;

public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findViewById(R.id.backgroundImageBlurred).setRenderEffect(RenderEffect.createBlurEffect(80, 80, Shader.TileMode.CLAMP));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findViewById(R.id.backgroundWhiteImageBlurred).setRenderEffect(RenderEffect.createBlurEffect(80, 80, Shader.TileMode.CLAMP));
        }


        ImageView menuButton = findViewById(R.id.menuButton);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_map);

        menuButton.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else if (id == R.id.nav_notes) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            finish();
        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(this, CalendarActivity.class));
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}