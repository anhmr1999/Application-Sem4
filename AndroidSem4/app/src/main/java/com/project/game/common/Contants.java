package com.project.game.common;

import android.content.Context;
import android.net.ConnectivityManager;

import com.project.game.entity.LevelHard;
import com.project.game.entity.User;

public class Contants {
    public static boolean Music;
    public static LevelHard flappyBirdLevel;
    public static LevelHard _2048Level;
    public static LevelHard knowLevel;
    public static User User;
    public static int Screen_Width;
    public static int Screen_Height;

    public static boolean IsNetworkConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
