package com.example.medimantra;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SessionCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_complete);

        // Get values from Intent
        int meditatedMinutes = getIntent().getIntExtra("MEDITATED_MINUTES", 0);
        int meditatedSeconds = getIntent().getIntExtra("MEDITATED_SECONDS", 0);

        // Get references
        TextView timeText = findViewById(R.id.meditationTimeText);
        ImageView closeButton = findViewById(R.id.closeButton);

        // Set time in format
        String formattedTime = String.format("%02d mins %02d sec", meditatedMinutes, meditatedSeconds);
        timeText.setText(formattedTime);

        // Close the screen when clicking on the button
        closeButton.setOnClickListener(v -> finish());
    }
}
