package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FlappyBirdActivity extends AppCompatActivity {
    public static TextView txtScore, endScore;
    public static RelativeLayout endgameLayout;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_bird_home);
    }

    public void PlayFlappyBird(View view){
        setContentView(R.layout.activity_flappy_bird);
        txtScore = findViewById(R.id.txtFlappyBirdScore);
        endgameLayout = findViewById(R.id.flappybirdEndGame);
        endScore = findViewById(R.id.flappybirdOverScore);

        endgameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_flappy_bird_home);
            }
        });
    }
}