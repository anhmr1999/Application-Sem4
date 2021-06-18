package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.project.game.common.Contants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Contants.Screen_Height = dm.heightPixels;
        Contants.Screen_Width = dm.widthPixels;
        setContentView(R.layout.activity_main);
    }

    public void ToFlappyBird(View view){
        Intent intent = new Intent(MainActivity.this, FlappyBirdActivity.class);
        startActivity(intent);
    }

    public void ToGame2048(View view){
        Intent intent = new Intent(MainActivity.this, Game2048Activity.class);
        startActivity(intent);
    }

    public void ToKnowledge(View view){
        Intent intent = new Intent(MainActivity.this, KnowledgeActivity.class);
        startActivity(intent);
    }
}