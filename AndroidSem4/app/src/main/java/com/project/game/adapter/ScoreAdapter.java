package com.project.game.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.game.R;

import java.util.List;

public class ScoreAdapter  extends BaseAdapter {
    private List<ScoreModel> list;

    public ScoreAdapter(List<ScoreModel> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View scoreView = null;
        if(convertView == null){
            scoreView = View.inflate(parent.getContext(), R.layout.highscore_item, null);
        } else {
            scoreView = convertView;
        }
        Log.e("CheckView"," View is null");
        ((TextView)scoreView.findViewById(R.id.highScoreName)).setText(list.get(position).getName());
        ((TextView)scoreView.findViewById(R.id.easyScore)).setText(list.get(position).getEasyScore());
        ((TextView)scoreView.findViewById(R.id.normalScore)).setText(list.get(position).getNormalScore());
        ((TextView)scoreView.findViewById(R.id.difficultScore)).setText(list.get(position).getDifficultScore());
        return scoreView;
    }
}

