package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.project.game.component.AchievementDialog;
import com.project.game.component.UserSettingDialog;
import com.project.game.common.ApiProviderImpl;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.datamanager.repository.UserRepository;
import com.project.game.entity.User;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private UserRepository userRepository;
    private SharedPreferences sp;
    public static ImageView userAvatar;
    public static TextView userName;
    private RelativeLayout flashScreen;
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

        if(Contants.IsNetworkConnected(MainActivity.this)){
            ApiProviderImpl apiProvider = new ApiProviderImpl(MainActivity.this);
            apiProvider.LoadScore();
            apiProvider.LoadAchievement();
        }

        sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        Contants.Music = sp.getBoolean("Music",true);
        Contants.localeKey = sp.getString("LanguageKey","en");

        changeMainActivity();
        changeLanguage();

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
    }

    public void ToFlappyBird(View view){
        flashScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, FlappyBirdActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void ToGame2048(View view){
        flashScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MainActivity.this, Game2048Activity.class);
                startActivity(intent);
            }
        }.start();
    }

    public void ToKnowledge(View view){
        flashScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
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
        flashScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                currentIsMain = false;
                setContentView(R.layout.activity_setting);
                ((TextView)findViewById(R.id.MusicSetting)).setText(Contants.Music? R.string.music_on : R.string.music_off);
                ((TextView)findViewById(R.id.LanguageSetting)).setText(Contants.localeKey=="vi"? R.string.languege_vi : R.string.languege_en);
            }
        }.start();
    }

    public void SettingChangeMusic(View view){
        if(Contants.Music){
            ((Button)view).setText(R.string.music_off);
        } else {
            ((Button)view).setText(R.string.music_on);
        }
        saveMusic();
    }

    public void backToMain(View view){
        changeMainActivity();
    }

    public void SettingChangeLanguage(View view){
        if(Contants.localeKey == "vi"){
            Contants.localeKey = "en";
        } else {
            Contants.localeKey = "vi";
        }

        if(sp == null){
            sp = MainActivity.this.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("LanguageKey",Contants.localeKey);
        editor.apply();

        changeLanguage();
        flashScreen = findViewById(R.id.flashScreen);
        flashScreen.setVisibility(View.VISIBLE);
        new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                changeMainActivity();
            }
        }.start();
    }

    public void Logout(View view){
        if(Contants.User.getId() == 0){
            userRepository.logout();
        }
        changeMainActivity();
    }

    public void SettingChangeProfile(View view){
        new UserSettingDialog(MainActivity.this).show();
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
                MainActivity.UpdateUser(MainActivity.this);
            } else {
                int userId = sp.getInt("UserId",0);
                Contants.User = userRepository.getUser(userId);
                if(Contants.User == null){
                    new UserSettingDialog(MainActivity.this).show();
                } else {
                    userName.setText(Contants.User.getName());
                    userAvatar.setImageResource(Contants.getAvatarResource());
                    MainActivity.UpdateUser(MainActivity.this);
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
        flashScreen = findViewById(R.id.flashScreen);
        userAvatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.name);
        if (Contants.Music){
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.un_mute);
        } else {
            ((ImageView) findViewById(R.id.music)).setImageResource(R.drawable.mute);
        }
        checkUser();
    }

    private void changeLanguage(){
        Locale locale = new Locale(Contants.localeKey);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config,this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if(currentIsMain){
            super.onBackPressed();
        }else{
            changeMainActivity();
        }
    }

    public void ViewAchievement(View view) {
        new AchievementDialog(MainActivity.this,new AchievementRepository(MainActivity.this), new UserAchievementRepository(MainActivity.this)).show();
    }

    public static void UpdateUser(Context context){
        ApiProviderImpl apiProvider = new ApiProviderImpl(context);
        User user = Contants.User;

        if(Contants.IsNetworkConnected(context)){
            if(user.getId() == 0){
                apiProvider.Login(user.getAccessToken(), user.getName(), user.getAvatar());
            } else {
                apiProvider.Login(user.getAccessToken(), user.getName(), user.getAvatar(), user.getId());
            }
        } else {
            UserRepository repository = new UserRepository(context);

            if(repository.getUser(user.getId()) == null){
                repository.add(user);
            } else {
                repository.update(user);
            }

            SharedPreferences sp = context.getSharedPreferences("CommonSetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("UserId",user.getId());
            editor.apply();

            MainActivity.userName.setText(Contants.User.getName());
            MainActivity.userAvatar.setImageResource(Contants.getAvatarResource());
        }
    }
}