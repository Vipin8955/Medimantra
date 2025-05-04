package com.example.medimantra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startNow = findViewById(R.id.btn_start_now);
        startNow.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        });

        setupCategoryClicks();
        setupBottomNavigation();
    }

    private void setupCategoryClicks() {
        LinearLayout categoryStudents = findViewById(R.id.category_students);
        LinearLayout categoryGratitude = findViewById(R.id.category_gratitude);
        LinearLayout categoryHealth = findViewById(R.id.category_health);
        LinearLayout categorySelfHealing = findViewById(R.id.category_self_healing);
        LinearLayout categoryBreathwork = findViewById(R.id.category_breathwork);
        LinearLayout categoryRelationship = findViewById(R.id.category_relationship);

        categoryStudents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentsActivity.class);
            startActivity(intent);
        });

        categoryGratitude.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GratitudeActivity.class);
            startActivity(intent);
        });

        categoryHealth.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HealthActivity.class);
            startActivity(intent);
        });

        categorySelfHealing.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelfHealingActivity.class);
            startActivity(intent);
        });

        categoryBreathwork.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BreathWorkActivity.class);
            startActivity(intent);
        });

        categoryRelationship.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RelationshipActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);


        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Already on MainActivity
                return true;
            } else if (itemId == R.id.nav_meditate) {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            }

            return false;
        });
    }

}
