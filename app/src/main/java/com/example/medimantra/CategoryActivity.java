package com.example.medimantra;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exercises = new ArrayList<>();
        exercises.add(new Exercise("Downward Facing Dog", R.drawable.img, "https://youtu.be/ayQoxw8sRTk?si=8X-0ut9_jW2Ait56"));
        exercises.add(new Exercise("Lotus Pose", R.drawable.img_1, "https://youtu.be/hqRq5uoSako?si=iG-rsn3Zjx9Y6H3O"));
        exercises.add(new Exercise("Quad Stretch", R.drawable.img_2, "https://youtu.be/XEMMNQsSEfk?si=beJBiBrURwqNvBng"));
        exercises.add(new Exercise("Butterfly Pose", R.drawable.img_3, "https://youtu.be/E3611YwA51E?si=zfT05clL0OKEwOgC"));
        exercises.add(new Exercise("Shoulder Stretch", R.drawable.img_4, "https://youtu.be/6jHsraw2NIk?si=i9bFnQcFQMaIivMX"));

        recyclerView.setAdapter(new ExerciseAdapter(this, exercises));
    }
    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);


        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.nav_meditate) {
                startActivity(new Intent(CategoryActivity.this, CategoryActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                return true;
            }

            return false;
        });
    }
}