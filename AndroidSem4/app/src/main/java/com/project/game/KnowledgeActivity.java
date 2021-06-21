package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.game.adapter.AnswerAdapter;
import com.project.game.entity.Question;
import com.project.game.gamecontroll.Knowledge;

public class KnowledgeActivity extends AppCompatActivity {
    public TextView txt_Question;
    public ListView lst_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_home);
    }

    public void PlayKnowledge(View view){
        setContentView(R.layout.activity_knowledge);
        Knowledge.getDataGame().init(KnowledgeActivity.this);
        txt_Question = findViewById(R.id.txt_question);
        lst_answer = findViewById(R.id.lst_answer);
        lst_answer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(KnowledgeActivity.this, "Fix", Toast.LENGTH_SHORT);
            }
        });
        changeQuestion();
    }

    private void changeQuestion(){
        Question question = Knowledge.getDataGame().changeQuestion();
        txt_Question.setText(question.getContent());
        lst_answer.setAdapter(new AnswerAdapter(question.getAnswers()));
    }

    public void help5050(View view){
        Toast.makeText(KnowledgeActivity.this, "Help 50/50", Toast.LENGTH_SHORT);
    }

    public void helpChange(View view){
        Toast.makeText(KnowledgeActivity.this, "Help Change", Toast.LENGTH_SHORT);
    }

    public void helpSkip(View view){
        Toast.makeText(KnowledgeActivity.this, "Help Skip", Toast.LENGTH_SHORT);
    }
}