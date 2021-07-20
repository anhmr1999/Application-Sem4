package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.component.AchievementDialog;
import com.project.game.component.LevelAdapter;
import com.project.game.component.ScoreAdapter;
import com.project.game.component.ScoreModel;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.entity.Score;
import com.project.game.gamecontroll.Knowledge;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeActivity extends AppCompatActivity {
    public static TextView txt_Question, txt_Score, txt_EndCore, txtTimer;
    public static ListView lst_answer;
    public static RelativeLayout GameKnowLedgeOver;
    private LevelHardRepository levelHardRepository;
    private SharedPreferences sp;
    public static AchievementDialog dialog;
    private int levelId;
    private ScoreRepository scoreRepository;
    public static boolean allowBack, isPlayGame, gameOver;
    private RelativeLayout flashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allowBack = true;
        isPlayGame = gameOver = false;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_knowledge_home);
        flashScreen = findViewById(R.id.flashScreen);
        levelHardRepository = new LevelHardRepository(KnowledgeActivity.this);
        scoreRepository = new ScoreRepository(KnowledgeActivity.this);
        sp = KnowledgeActivity.this.getSharedPreferences("knowLedgeSetting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        }
        if(levelId == 0) {
            levelId = levelHardRepository.getLevelGame().get(0).getId();
            sp = KnowledgeActivity.this.getSharedPreferences("knowLedgeSetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelId);
            editor.apply();
        }
        Contants.knowLevel = levelHardRepository.getLevel(levelId);
    }

    public void PlayKnowledge(View view){
        allowBack = gameOver = false;
        isPlayGame = true;
        setContentView(R.layout.activity_knowledge);
        Knowledge.getDataGame().init(KnowledgeActivity.this);
        dialog = new AchievementDialog(KnowledgeActivity.this,null);
        txt_Question = findViewById(R.id.txt_question);
        txtTimer = findViewById(R.id.txtTimer);
        txt_Score = findViewById(R.id.txt_Score);
        txt_EndCore = findViewById(R.id.KnowLedgeEndScore);
        lst_answer = findViewById(R.id.lst_answer);
        GameKnowLedgeOver = findViewById(R.id.GameKnowLedgeOver);
        Knowledge.getDataGame().changeQuestion();
    }

    public void ContinueGame(View view){
        new CountDownTimer(1000, 100){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setContentView(R.layout.activity_knowledge_home);
                flashScreen = findViewById(R.id.flashScreen);
                allowBack = true;
            }
        }.start();
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
        flashScreen.setVisibility(View.VISIBLE);
        allowBack = false;
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                setContentView(R.layout.activity_level);
                findViewById(R.id.levelLayout).setBackgroundResource(R.drawable.knowledge_background);
                ((ListView)findViewById(R.id.Lst_Level)).setAdapter(new LevelAdapter(levelHardRepository.getLevelGame(), levelId));
                ((ListView)findViewById(R.id.Lst_Level)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        levelId = (int) ((ListView) findViewById(R.id.Lst_Level)).getAdapter().getItemId(position);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("levelId",levelId);
                        editor.apply();
                        Contants.knowLevel = levelHardRepository.getLevel(levelId);
                        setContentView(R.layout.activity_knowledge_home);
                        flashScreen = findViewById(R.id.flashScreen);
                        allowBack = false;
                    }
                });
            }
        }.start();
    }

    public void viewScore(View view){
        flashScreen.setVisibility(View.VISIBLE);
        allowBack = false;
        List<ScoreModel> scoreModels = new ArrayList<>();
        for (Score score : scoreRepository.GetScore(3)) {
            boolean checkHas = false;
            for (ScoreModel scoremodel : scoreModels){
                if(scoremodel.getId() == score.getUserId()){
                    checkHas = true;
                    if(score.getLevelHard().getName().toLowerCase().equals("easy")){
                        scoremodel.setEasyScore(score.getScore());
                    } else if(score.getLevelHard().getName().toLowerCase().equals("normal")){
                        scoremodel.setNormalScore(score.getScore());
                    } else {
                        scoremodel.setDifficultScore(score.getScore());
                    }
                    continue;
                }
            }
            if(!checkHas){
                ScoreModel model = new ScoreModel(score.getUserId(), score.getUser().getName(),0,0,0);
                if(score.getLevelHard().getName().equals("easy")){
                    model.setEasyScore(score.getScore());
                } else if(score.getLevelHard().getName().equals("normal")){
                    model.setNormalScore(score.getScore());
                } else {
                    model.setDifficultScore(score.getScore());
                }
                scoreModels.add(model);
            }
        }
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                setContentView(R.layout.activity_highscore);
                if(Contants.User != null){
                    for ( Score score:scoreRepository.getScoreForUser(3,Contants.User.getId())) {
                        if(score.getLevelHard().getName().toLowerCase().equals("easy")){
                            ((TextView) findViewById(R.id.yourEasyScore)).setText(""+score.getScore());
                        } else if(score.getLevelHard().getName().toLowerCase().equals("normal")){
                            ((TextView) findViewById(R.id.yourNormalScore)).setText(""+score.getScore());
                        } else {
                            ((TextView) findViewById(R.id.yourDifficultScore)).setText(""+score.getScore());
                        }
                    }
                }
                findViewById(R.id.highScoreLayout).setBackgroundResource(R.drawable.knowledge_background);
                ((ListView) findViewById(R.id.Lst_HighScore)).setAdapter(new ScoreAdapter(scoreModels));
            }
        }.start();
    }

    public void BackToHome(View view){
        setContentView(R.layout.activity_knowledge_home);
        flashScreen = findViewById(R.id.flashScreen);
        allowBack= true;
    }

    public void showQuestionManager(View view){
        flashScreen.setVisibility(View.VISIBLE);
        allowBack = false;
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                flashScreen.setVisibility(View.INVISIBLE);
                setContentView(R.layout.activity_question_manager);
            }
        }.start();
    }

    public void BackToMainActivity(View view){
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(allowBack){
            super.onBackPressed();
        } else {
            if(isPlayGame){
                if(gameOver){
                    setContentView(R.layout.activity_knowledge_home);
                    flashScreen = findViewById(R.id.flashScreen);
                    allowBack = !allowBack;
                } else {
                    Knowledge.getDataGame().EndGame();
                    gameOver = true;
                }
            } else {
                setContentView(R.layout.activity_knowledge_home);
                flashScreen = findViewById(R.id.flashScreen);
                allowBack = !allowBack;
            }
        }
    }
}