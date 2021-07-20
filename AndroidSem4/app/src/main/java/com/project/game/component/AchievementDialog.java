package com.project.game.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.project.game.R;
import com.project.game.common.Contants;
import com.project.game.entity.Achievement;

import java.util.List;

public class AchievementDialog extends Dialog{
    public AchievementDialog(@NonNull Context context, List<Achievement> achievements) {
        super(context);
        setContentView(R.layout.achievement_dialog);
        if(achievements != null){
            setAchievement(achievements);
        }
        ((Button)findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(Contants.User == null){
            findViewById(R.id.shareWithFacebook).setVisibility(View.INVISIBLE);
        }
    }

    public void setAchievement(List<Achievement> achievements){
        AchievementAdapter adapter = new AchievementAdapter((achievements));
        ((ListView)findViewById(R.id.lstAchievement)).setAdapter(adapter);
    }
}
