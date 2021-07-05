package com.project.game.common;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.project.game.entity.Achievement;

import java.util.List;

public class AchievementDialog extends Dialog{
    private List<Achievement> LstAchievement;

    public AchievementDialog(@NonNull Context context, List<Achievement> achievements) {
        super(context);
        LstAchievement = achievements;
    }
}
