package com.project.game.component;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.game.R;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.entity.Achievement;

import java.util.List;

public class ViewAchievementAdapter  extends BaseAdapter {

    private List<Achievement> achievements;
    private UserAchievementRepository _userAchievementRepository;

    public ViewAchievementAdapter(AchievementRepository achievementRepository, UserAchievementRepository userAchievementRepository){
        achievements = achievementRepository.getAchievement();
        _userAchievementRepository = userAchievementRepository;
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
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.view_achievement_item, null);
        }
        Achievement achievement = getItem(position);
        ((TextView) convertView.findViewById(R.id.achievementName)).setText(achievement.getName());
        ((TextView) convertView.findViewById(R.id.achievementDescription)).setText(achievement.getTutorial());
        if(achievement.getGameId() == 1){
            ((ImageView) convertView.findViewById(R.id.achievementImg)).setImageResource(R.drawable.bird);
        } else if(achievement.getGameId() == 2) {
            ((ImageView) convertView.findViewById(R.id.achievementImg)).setImageResource(R.drawable.logo2048);
        } else if(achievement.getGameId() == 3){
            ((ImageView) convertView.findViewById(R.id.achievementImg)).setImageResource(R.drawable.knowledge_logo);
        } else {
            ((ImageView) convertView.findViewById(R.id.achievementImg)).setImageResource(R.drawable.user4);
        }
        if(_userAchievementRepository.find(Contants.User.getId(), achievement.getId())){
            convertView.setBackgroundResource(R.drawable.level_bg);
            ((TextView) convertView.findViewById(R.id.achievementName)).setTextColor(Color.BLACK);
            ((TextView) convertView.findViewById(R.id.achievementDescription)).setTextColor(Color.BLACK);
        }

        return convertView;
    }
}
