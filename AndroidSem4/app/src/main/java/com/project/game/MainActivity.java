package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.project.game.adapter.UserSettingDialog;
import com.project.game.common.ApiProviderImpl;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.UserRepository;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private SharedPreferences sp;
    public static ImageView userAvatar;
    public static TextView userName;
    private RelativeLayout flasshScreen;
    private boolean currentIsMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository = new UserRepository(MainActivity.this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Contants.Screen_Height = dm.heightPixels;
        Contants.Screen_Width = dm.widthPixels;

        sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        Contants.Music = sp.getBoolean("Music",true);

        changeMainActivity();

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

        if(Contants.IsNetworkConnected(MainActivity.this)){
            ApiProviderImpl apiProvider = new ApiProviderImpl(MainActivity.this);
            apiProvider.LoadScore();
        }
    }

    public void ToFlappyBird(View view){
        flasshScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flasshScreen.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, FlappyBirdActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void ToGame2048(View view){
        flasshScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flasshScreen.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, Game2048Activity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void ToKnowledge(View view){
        flasshScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flasshScreen.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, KnowledgeActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void changeMute(View view){
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        }
        saveMusic();
    }

    public void ToSetting(View view){
        flasshScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flasshScreen.setVisibility(View.INVISIBLE);
                currentIsMain = false;
                setContentView(R.layout.activity_setting);
                ((TextView)findViewById(R.id.MusicSetting)).setText("MUSIC: " + (Contants.Music?"ON":"OFF"));
            }
        }.start();
    }

    public void SettingChangeMusic(View view){
        if(Contants.Music){
            ((Button)view).setText("MUSIC: OFF");
        } else {
            ((Button)view).setText("MUSIC: ON");
        }
        saveMusic();
    }

    public void backToMain(View view){
        changeMainActivity();
    }

    public void SettingChangeLanguage(View view){

    }

    public void Logout(View view){

    }

    public void SettingChangeProfile(View view){

    }

    private void saveMusic(){
        Contants.Music = !Contants.Music;
        if(sp == null){
            sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Music",Contants.Music);
        editor.apply();
    }

    private void checkUser(){
        if(Contants.User == null){
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken != null && !accessToken.isExpired()){
                Contants.User = userRepository.getUser(accessToken.getToken());
            } else {
                Contants.User = userRepository.getUser(0);
                if(Contants.User == null){
                    new UserSettingDialog(MainActivity.this, false).show();
                } else {
                    userName.setText(Contants.User.getName());
                    userAvatar.setImageResource(Contants.getAvatarResource());
                }
            }
        } else {
            userName.setText(Contants.User.getName());
            userAvatar.setImageResource(Contants.getAvatarResource());
        }
    }

    private void changeMainActivity(){
        setContentView(R.layout.activity_main);
        currentIsMain = true;
        flasshScreen = findViewById(R.id.flashScreen);
        userAvatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.name);
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        }
        checkUser();
    }

    @Override
    public void onBackPressed() {
        if(currentIsMain){
            super.onBackPressed();
        }else{
            changeMainActivity();
        }
    }
}