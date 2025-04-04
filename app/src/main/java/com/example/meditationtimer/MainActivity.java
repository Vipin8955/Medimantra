package com.example.meditationtimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private String selectedMusic;
    private int selectedMusicResId;
    private int selectedImageResId;
    private EditText warmUpInput, meditationInput, silenceInput;
    private Button startButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        warmUpInput = findViewById(R.id.warmUpInput);
        meditationInput = findViewById(R.id.meditationInput);
        silenceInput = findViewById(R.id.silenceInput);
        startButton = findViewById(R.id.startButton);
        backButton = findViewById(R.id.backButton);

        // Set glossy green button
        startButton.setBackgroundResource(R.drawable.button_glossy_green);

        setupMusicSelection();
        setupStartButton();

        // Close the activity when the back button is pressed
        backButton.setOnClickListener(v -> finish());
    }

    private void setupMusicSelection() {
        final String[] musicOptions = {
                "Joy of Water", "Ocean Breeze", "Calm Forest",
                "Deep Relaxation", "Soft Rain", "Zen Garden"
        };

        final int[] musicFiles = {
                R.raw.joy_of_water, R.raw.ocean_breeze, R.raw.calm_forest,
                R.raw.deep_relaxation, R.raw.soft_rain, R.raw.zen_garden
        };

        final int[] musicIcons = {
                R.drawable.bg_joy_of_water, R.drawable.bg_ocean_breeze, R.drawable.bg_calm_forest,
                R.drawable.bg_deep_relaxation, R.drawable.bg_soft_rain, R.drawable.bg_zen_garden
        };

        final int[] backgroundImages = {
                R.drawable.bg_joy_of_water, R.drawable.bg_ocean_breeze, R.drawable.bg_calm_forest,
                R.drawable.bg_deep_relaxation, R.drawable.bg_soft_rain, R.drawable.bg_zen_garden
        };

        LinearLayout musicContainer = findViewById(R.id.musicContainer);
        musicContainer.removeAllViews(); // Clear previous views

        for (int i = 0; i < musicOptions.length; i++) {
            View musicItem = getLayoutInflater().inflate(R.layout.item_music, musicContainer, false);
            ImageView musicIcon = musicItem.findViewById(R.id.musicIcon);
            TextView musicTitle = musicItem.findViewById(R.id.musicTitle);

            musicIcon.setImageResource(musicIcons[i]);
            musicTitle.setText(musicOptions[i]);
            musicIcon.setClipToOutline(true);

            final int index = i;
            musicItem.setOnClickListener(v -> {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                selectedMusic = musicOptions[index];
                selectedMusicResId = musicFiles[index];
                selectedImageResId = backgroundImages[index];

                mediaPlayer = MediaPlayer.create(MainActivity.this, selectedMusicResId);
                showAlert("Selected: " + selectedMusic);
            });

            // Make both the text and icon clickable
            musicIcon.setOnClickListener(v -> musicItem.performClick());
            musicTitle.setOnClickListener(v -> musicItem.performClick());

            musicContainer.addView(musicItem);
        }
    }

    private void setupStartButton() {
        startButton.setOnClickListener(v -> {
            String warmUpTime = warmUpInput.getText().toString();
            String meditationTime = meditationInput.getText().toString();
            String silenceTime = silenceInput.getText().toString();

            if (warmUpTime.isEmpty() || meditationTime.isEmpty() || silenceTime.isEmpty()) {
                showAlert("Please enter all time values.");
                return;
            }

            if (selectedMusic == null) {
                showAlert("Please select a music track.");
                return;
            }

            Intent intent = new Intent(MainActivity.this, MeditationActivity.class);
            intent.putExtra("WARMUP_TIME", Integer.parseInt(warmUpTime));
            intent.putExtra("MEDITATION_TIME", Integer.parseInt(meditationTime));
            intent.putExtra("SILENCE_TIME", Integer.parseInt(silenceTime));
            intent.putExtra("MUSIC_RES_ID", selectedMusicResId);
            intent.putExtra("IMAGE_RES_ID", selectedImageResId);
            startActivity(intent);
        });
    }


    private void showAlert(String message) {
        new android.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
