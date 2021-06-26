package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(MainActivity.this);
        SharedPreferences sp = MainActivity.this.getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        if(sp != null){
            userId = sp.getInt("userId",0);
            if(userId != 0){
                Contants.user = userRepository.getUser(userId);
            } else {
                Contants.user = null;
            }
        }

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