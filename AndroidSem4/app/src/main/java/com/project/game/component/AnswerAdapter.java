package com.project.game.component;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.game.R;
import com.project.game.entity.Answer;

import java.util.List;

public class AnswerAdapter extends BaseAdapter {
    private List<Answer> answers;

    public AnswerAdapter(List<Answer> answers){
        this.answers = answers;
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Answer getItem(int position) {
        return answers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return answers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View answerView = null;
        if(convertView == null){
            answerView = View.inflate(parent.getContext(), R.layout.answer_item, null);
        } else {
            answerView = convertView;
        }

        Answer answer = getItem(position);
        ((TextView) (answerView.findViewById(R.id.btn_answer))).setText(answer.getContent());

        return answerView;
    }
}
