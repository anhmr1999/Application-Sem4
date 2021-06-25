package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.adapter.LevelAdapter;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.entity.Answer;
import com.project.game.gamecontroll.Knowledge;

public class KnowledgeActivity extends AppCompatActivity {
    public static TextView txt_Question, txt_Score, txt_EndCore;
    public static ListView lst_answer;
    public static RelativeLayout GameKnowLedgeOver;
    private LevelHardRepository levelHardRepository;
    private AdapterView.OnItemClickListener itemClickListener;
    private SharedPreferences sp;
    private int levelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_home);
        levelHardRepository = new LevelHardRepository(KnowledgeActivity.this);
        sp = KnowledgeActivity.this.getSharedPreferences("knowLedgeSetting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        } else {
            levelId = levelHardRepository.getLevelGame(2).get(0).getId();
            sp = KnowledgeActivity.this.getSharedPreferences("knowLedgeSetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelHardRepository.getLevelGame(1).get(0).getId());
            editor.apply();
        }
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

    public void ChangeLevel(View view){
        setContentView(R.layout.activity_level);
        findViewById(R.id.levelLayout).setBackgroundResource(R.drawable.knowledge_background);
        ((ListView)findViewById(R.id.Lst_Level)).setAdapter(new LevelAdapter(levelHardRepository.getLevelGame(2), levelId));
        ((ListView)findViewById(R.id.Lst_Level)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                levelId = (int) ((ListView) findViewById(R.id.Lst_Level)).getAdapter().getItemId(position);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("levelId",levelId);
                editor.apply();
                setContentView(R.layout.activity_knowledge_home);
            }
        });
    }
}