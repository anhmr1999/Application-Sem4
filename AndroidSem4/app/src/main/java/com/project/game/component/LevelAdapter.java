package com.project.game.component;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.game.R;
import com.project.game.entity.LevelHard;

import java.util.List;

public class LevelAdapter extends BaseAdapter {
    private List<LevelHard> levels;
    private int currentLevel;

    public LevelAdapter(List<LevelHard> level, int currentLevel){
        this.levels = level;
        this.currentLevel = currentLevel;
    }
    @Override
    public int getCount() {
        return levels.size();
    }

    @Override
    public LevelHard getItem(int position) {
        return levels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return levels.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View levelView = null;
        if(convertView == null){
            levelView = View.inflate(parent.getContext(), R.layout.level_item, null);
        } else {
            levelView = convertView;
        }

        LevelHard level = getItem(position);
        ((TextView) (levelView.findViewById(R.id.txt_level))).setText(level.getName());
        if(getItemId(position) == currentLevel){
            levelView.findViewById(R.id.txt_level).setBackgroundResource(R.drawable.level_bg_choose);
        } else {
            levelView.findViewById(R.id.txt_level).setBackgroundResource(R.drawable.level_bg);
        }
        return levelView;
    }
}
