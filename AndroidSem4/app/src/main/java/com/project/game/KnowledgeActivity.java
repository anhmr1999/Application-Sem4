package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.game.adapter.AnswerAdapter;
import com.project.game.entity.Answer;
import com.project.game.entity.Question;
import com.project.game.gamecontroll.Knowledge;

public class KnowledgeActivity extends AppCompatActivity {
    public static TextView txt_Question, txt_Score, txt_EndCore;
    public static ListView lst_answer;
    public static RelativeLayout GameKnowLedgeOver;
    private AdapterView.OnItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_home);
    }

    public void PlayKnowledge(View view){
        setContentView(R.layout.activity_knowledge);
        Knowledge.getDataGame().init(KnowledgeActivity.this);
        txt_Question = findViewById(R.id.txt_question);
        txt_Score = findViewById(R.id.txt_Score);
        txt_EndCore = findViewById(R.id.KnowLedgeEndScore);
        lst_answer = findViewById(R.id.lst_answer);
        GameKnowLedgeOver = findViewById(R.id.GameKnowLedgeOver);
        Knowledge.getDataGame().changeQuestion();
        itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lst_answer.getChildAt(position).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_choose);

                new CountDownTimer(2000,100){

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        if(((Answer)lst_answer.getItemAtPosition(position)).isCorrect()){
                            lst_answer.getChildAt(position).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_true);
                            new CountDownTimer(2000, 100) {
                                @Override
                                public void onTick(long l) {}

                                @Override
                                public void onFinish() {
                                    Knowledge.getDataGame().CorrectAnswer();
                                    Knowledge.getDataGame().changeQuestion();
                                }
                            }.start();
                        } else {
                            lst_answer.getChildAt(position).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_danger);
                            for (int i = 0; i< KnowledgeActivity.lst_answer.getChildCount(); i++){
                                if(((Answer)lst_answer.getItemAtPosition(i)).isCorrect()){
                                    lst_answer.getChildAt(i).findViewById(R.id.btn_answer).setBackgroundResource(R.drawable.bg_answer_true);
                                }
                            }
                            new CountDownTimer(2000, 100) {
                                @Override
                                public void onTick(long l) {}

                                @Override
                                public void onFinish() {
                                    Knowledge.getDataGame().EndGame();
                                }
                            }.start();
                        }
                    }
                }.start();
            }
        };
        KnowledgeActivity.lst_answer.setOnItemClickListener(itemClickListener);
    }

    public void ContinueGame(View view){
        setContentView(R.layout.activity_knowledge_home);
    }

    public void help5050(View view){
        findViewById(R.id.know5050).setVisibility(View.INVISIBLE);
        Knowledge.getDataGame().use5050();
    }

    public void helpChange(View view){
        findViewById(R.id.knowChange).setVisibility(View.INVISIBLE);
        Knowledge.getDataGame().useChangeQuestion();
    }

    public void helpSkip(View view){
        findViewById(R.id.knowSkip).setVisibility(View.INVISIBLE);
        Knowledge.getDataGame().useSkipQuestion();
    }
}