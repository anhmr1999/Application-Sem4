package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private CallbackManager callbackManager;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(MainActivity.this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Contants.Screen_Height = dm.heightPixels;
        Contants.Screen_Width = dm.widthPixels;

        setContentView(R.layout.activity_main);
        sp = MainActivity.this.getSharedPreferences("MusicSetting", Context.MODE_PRIVATE);
        Contants.Music = sp.getBoolean("Music",true);
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Contants.User = userRepository.getUser(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null && !accessToken.isExpired()){
            Contants.User = userRepository.getUser(accessToken.getToken());
        }
        Log.e("Main Check Network", "Connection : " + Contants.IsNetworkConnected(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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

    public void changeMute(View view){
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        }
        Contants.Music = !Contants.Music;
        if(sp == null){
            sp = MainActivity.this.getSharedPreferences("MusicSetting", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Music",Contants.Music);
        editor.apply();
    }
}