package com.example.medimantra;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MeditationActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private boolean isPaused = false;
    private long timeLeftInMillis;

    private int warmUpTime, meditationTime, silenceTime;
    private long totalTime, meditationEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        // UI Elements
        TextView timerText = findViewById(R.id.timerText);
        Button pauseButton = findViewById(R.id.pauseButton);
        ImageView backgroundImage = findViewById(R.id.backgroundImage);

        // Get Intent data
        Intent intent = getIntent();
        warmUpTime = intent.getIntExtra("WARMUP_TIME", 0) * 60000;
        meditationTime = intent.getIntExtra("MEDITATION_TIME", 0) * 60000;
        silenceTime = intent.getIntExtra("SILENCE_TIME", 0) * 60000;
        int musicResId = intent.getIntExtra("MUSIC_RES_ID", 0);
        int imageResId = intent.getIntExtra("IMAGE_RES_ID", 0);

        // Set background image
        backgroundImage.setImageResource(imageResId);

        // Calculate times
        totalTime = warmUpTime + meditationTime + silenceTime;
        meditationEndTime = warmUpTime + meditationTime;

        // Play music
        if (musicResId != 0) {
            mediaPlayer = MediaPlayer.create(this, musicResId);
            mediaPlayer.start();
        }

        // Start timer
        startTimer(totalTime, timerText, pauseButton);

        // Pause/Resume
        pauseButton.setOnClickListener(v -> togglePauseResume(timerText, pauseButton));
    }

    private void startTimer(long duration, TextView timerText, Button pauseButton) {
        timeLeftInMillis = duration;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText(millisUntilFinished, timerText);

                if (millisUntilFinished <= meditationEndTime && millisUntilFinished > silenceTime) {
                    // Meditation phase
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(MeditationActivity.this, getIntent().getIntExtra("MUSIC_RES_ID", 0));
                        mediaPlayer.start();
                    }
                } else if (millisUntilFinished <= silenceTime) {
                    // Silence phase
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }

            @Override
            public void onFinish() {
                timerText.setText(getString(R.string.zero_timer));
                int meditatedMinutes = (int) (totalTime / 1000) / 60;
                int meditatedSeconds = (int) (totalTime / 1000) % 60;

                Intent intent = new Intent(MeditationActivity.this, SessionCompleteActivity.class);
                intent.putExtra("MEDITATED_MINUTES", meditatedMinutes);
                intent.putExtra("MEDITATED_SECONDS", meditatedSeconds);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void togglePauseResume(TextView timerText, Button pauseButton) {
        if (isPaused) {
            startTimer(timeLeftInMillis, timerText, pauseButton);
            if (mediaPlayer != null) mediaPlayer.start();
            pauseButton.setText(getString(R.string.pause));
        } else {
            countDownTimer.cancel();
            if (mediaPlayer != null) mediaPlayer.pause();
            pauseButton.setText(getString(R.string.resume));
        }
        isPaused = !isPaused;
    }

    private void updateTimerText(long millisUntilFinished, TextView timerText) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        timerText.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
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
                Log.e("MeditationActivity", "MediaPlayer error", e);
            }
        }
    }
}
