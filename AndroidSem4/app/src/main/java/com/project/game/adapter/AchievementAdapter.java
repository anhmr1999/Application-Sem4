package com.project.game.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.game.R;
import com.project.game.entity.Achievement;

import java.util.List;

public class AchievementAdapter  extends BaseAdapter {
    private List<Achievement> achievements;

    public AchievementAdapter(List<Achievement> achievements){
        this.achievements = achievements;
    }

    @Override
    public int getCount() {
        return achievements.size();
    }

    @Override
    public Achievement getItem(int position) {
        return achievements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return achievements.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View achievementView = null;

        if(convertView == null){
            achievementView = View.inflate(parent.getContext(), R.layout.achievement_item, null);
        } else {
            achievementView = convertView;
        }

        ((TextView) achievementView.findViewById(R.id.achievementName)).setText(getItem(position).getName());
        return achievementView;
    }
}
