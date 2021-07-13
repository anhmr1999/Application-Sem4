package com.project.game.common;

import android.content.Context;
import android.net.ConnectivityManager;

import com.project.game.R;
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

    public static int getAvatarResource(){
        int result = 1;
        switch (Contants.User.getAvatar()){
            case 1:
                result = R.drawable.bird;
                break;
            case 2:
                result = R.drawable.user1;
                break;
            case 3:
                result = R.drawable.user2;
                break;
            case 4:
                result = R.drawable.user3;
                break;
            case 5:
                result = R.drawable.user4;
                break;
            default:
                result = R.drawable.bird;
                break;
        }
        return result;
    }
}
