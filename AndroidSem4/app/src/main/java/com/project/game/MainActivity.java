package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.project.game.adapter.UserSettingDialog;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private SharedPreferences sp;
    public static ImageView userAvatar;
    public static TextView userName;

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
        userAvatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.name);
        sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        Contants.Music = sp.getBoolean("Music",true);
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        }

        /*FacebookSdk.sdkInitialize(getApplicationContext());
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
        });*/
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null && !accessToken.isExpired()){
            Contants.User = userRepository.getUser(accessToken.getToken());
        } else {
            Contants.User = userRepository.getUser(0);
            if(Contants.User == null){
                new UserSettingDialog(MainActivity.this, false).show();
            }
        }
        //Log.e("Main Check Network", "Connection : " + Contants.IsNetworkConnected(MainActivity.this));
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
            sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Music",Contants.Music);
        editor.apply();
    }
}