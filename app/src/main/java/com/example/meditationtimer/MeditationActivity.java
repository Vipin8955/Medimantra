package com.example.meditationtimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MeditationActivity extends AppCompatActivity {

    private TextView timerText;
    private Button pauseButton;
    private ImageView backgroundImage;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private boolean isPaused = false;
    private long timeLeftInMillis;

    private int warmUpTime, meditationTime, silenceTime;
    private long totalTime, warmUpEndTime, meditationEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        // Get references
        timerText = findViewById(R.id.timerText);
        pauseButton = findViewById(R.id.pauseButton);
        backgroundImage = findViewById(R.id.backgroundImage);

        // Get values from Intent
        Intent intent = getIntent();
        warmUpTime = intent.getIntExtra("WARMUP_TIME", 0) * 60000; // Convert to milliseconds
        meditationTime = intent.getIntExtra("MEDITATION_TIME", 0) * 60000;
        silenceTime = intent.getIntExtra("SILENCE_TIME", 0) * 60000;
        int musicResId = intent.getIntExtra("MUSIC_RES_ID", 0);
        int imageResId = intent.getIntExtra("IMAGE_RES_ID", 0);

        // Set Background Image
        backgroundImage.setImageResource(imageResId);

        // Calculate total times
        totalTime = warmUpTime + meditationTime + silenceTime;
        warmUpEndTime = warmUpTime;
        meditationEndTime = warmUpTime + meditationTime;

        // Play Selected Music
        if (musicResId != 0) {
            mediaPlayer = MediaPlayer.create(this, musicResId);
            mediaPlayer.start();
        }

        // Start the full session timer
        startTimer(totalTime);

        // Pause / Resume Button
        pauseButton.setOnClickListener(v -> togglePauseResume());
    }

    private void startTimer(long duration) {
        timeLeftInMillis = duration;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText(millisUntilFinished);

                if (millisUntilFinished <= meditationEndTime && millisUntilFinished > silenceTime) {
                    // Meditation Phase
                    if (mediaPlayer == null) {  // Restart music if needed
                        mediaPlayer = MediaPlayer.create(MeditationActivity.this, getIntent().getIntExtra("MUSIC_RES_ID", 0));
                        mediaPlayer.start();
                    }
                } else if (millisUntilFinished <= silenceTime) {
                    // Silence Phase
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }

            @Override
            public void onFinish() {
                timerText.setText("00:00");

                // Calculate meditated time
                int meditatedMinutes = (int) (totalTime / 1000) / 60;
                int meditatedSeconds = (int) (totalTime / 1000) % 60;

                // Start SessionCompleteActivity with time details
                Intent intent = new Intent(MeditationActivity.this, SessionCompleteActivity.class);
                intent.putExtra("MEDITATED_MINUTES", meditatedMinutes);
                intent.putExtra("MEDITATED_SECONDS", meditatedSeconds);
                startActivity(intent);

                finish(); // Close the MeditationActivity
            }

        }.start();
    }

    private void togglePauseResume() {
        if (isPaused) {
            startTimer(timeLeftInMillis);
            if (mediaPlayer != null) mediaPlayer.start();
            pauseButton.setText("Pause");
        } else {
            countDownTimer.cancel();
            if (mediaPlayer != null) mediaPlayer.pause();
            pauseButton.setText("Resume");
        }
        isPaused = !isPaused;
    }

    private void updateTimerText(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        timerText.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
}
