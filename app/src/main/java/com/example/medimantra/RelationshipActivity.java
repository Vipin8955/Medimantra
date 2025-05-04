package com.example.medimantra;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class RelationshipActivity extends AppCompatActivity {

    MediaPlayer player1, player2, player3, player4;
    boolean isPlaying1 = false, isPlaying2 = false, isPlaying3 = false, isPlaying4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship);

        FrameLayout card1 = findViewById(R.id.card1);
        FrameLayout card2 = findViewById(R.id.card2);
        FrameLayout card3 = findViewById(R.id.card3);
        FrameLayout card4 = findViewById(R.id.card4);

        player1 = MediaPlayer.create(this, R.raw.music1);
        player2 = MediaPlayer.create(this, R.raw.music2);
        player3 = MediaPlayer.create(this, R.raw.music3);
        player4 = MediaPlayer.create(this, R.raw.music4);

        // Set click listeners on FrameLayouts
        card1.setOnClickListener(v -> toggleMusic(player1, 1));
        card2.setOnClickListener(v -> toggleMusic(player2, 2));
        card3.setOnClickListener(v -> toggleMusic(player3, 3));
        card4.setOnClickListener(v -> toggleMusic(player4, 4));
    }

    private void toggleMusic(MediaPlayer player, int playerNumber) {
        Log.d("Audio", "Clicked card " + playerNumber);

        // Pause all other players
        if (playerNumber != 1 && isPlaying1) {
            player1.pause();
            isPlaying1 = false;
        }
        if (playerNumber != 2 && isPlaying2) {
            player2.pause();
            isPlaying2 = false;
        }
        if (playerNumber != 3 && isPlaying3) {
            player3.pause();
            isPlaying3 = false;
        }
        if (playerNumber != 4 && isPlaying4) {
            player4.pause();
            isPlaying4 = false;
        }

        // Toggle selected player
        switch (playerNumber) {
            case 1:
                if (isPlaying1) {
                    player.pause();
                } else {
                    player.start();
                }
                isPlaying1 = !isPlaying1;
                break;
            case 2:
                if (isPlaying2) {
                    player.pause();
                } else {
                    player.start();
                }
                isPlaying2 = !isPlaying2;
                break;
            case 3:
                if (isPlaying3) {
                    player.pause();
                } else {
                    player.start();
                }
                isPlaying3 = !isPlaying3;
                break;
            case 4:
                if (isPlaying4) {
                    player.pause();
                } else {
                    player.start();
                }
                isPlaying4 = !isPlaying4;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player1 != null) player1.release();
        if (player2 != null) player2.release();
        if (player3 != null) player3.release();
        if (player4 != null) player4.release();
    }
}
