package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.adapter.AchievementDialog;
import com.project.game.adapter.LevelAdapter;
import com.project.game.adapter.ScoreAdapter;
import com.project.game.adapter.ScoreModel;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.entity.Score;

import java.util.ArrayList;
import java.util.List;

public class FlappyBirdActivity extends AppCompatActivity {
    public static TextView txtScore, endScore;
    public static RelativeLayout endgameLayout;
    public static AchievementDialog dialog;
    private MediaPlayer mediaPlayer;
    private LevelHardRepository levelHardRepository;
    private SharedPreferences sp;
    private int levelId;
    private ScoreRepository scoreRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_flappy_bird_home);
        levelHardRepository = new LevelHardRepository(FlappyBirdActivity.this);
        scoreRepository = new ScoreRepository(FlappyBirdActivity.this);
        sp = FlappyBirdActivity.this.getSharedPreferences("flappyBirdSetting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        }
        if(levelId == 0){
            levelId = levelHardRepository.getLevelGame(1).get(0).getId();
            sp = FlappyBirdActivity.this.getSharedPreferences("flappyBirdSetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelId);
            editor.apply();
        }
        Contants.flappyBirdLevel = levelHardRepository.getLevel(levelId);
        dialog = new AchievementDialog(FlappyBirdActivity.this,null);
    }

    public void PlayFlappyBird(View view){
        setContentView(R.layout.activity_flappy_bird);
        txtScore = findViewById(R.id.txtFlappyBirdScore);
        endgameLayout = findViewById(R.id.flappybirdEndGame);
        endScore = findViewById(R.id.flappybirdOverScore);

        endgameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_flappy_bird_home);
            }
        });
    }

    public void ChangeLevel(View view){
        setContentView(R.layout.activity_level);
        findViewById(R.id.levelLayout).setBackgroundResource(R.drawable.flappy_bird_backgroup);
        ((ListView)findViewById(R.id.Lst_Level)).setAdapter(new LevelAdapter(levelHardRepository.getLevelGame(1), levelId));
        ((ListView)findViewById(R.id.Lst_Level)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                levelId = (int) ((ListView) findViewById(R.id.Lst_Level)).getAdapter().getItemId(position);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("levelId",levelId);
                editor.apply();
                Contants.flappyBirdLevel = levelHardRepository.getLevel(levelId);
                setContentView(R.layout.activity_flappy_bird_home);
            }
        });
    }

    public void viewScore(View view){
        List<ScoreModel> scoreModels = new ArrayList<>();
        for (Score score : scoreRepository.GetScore(1)) {
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
        setContentView(R.layout.activity_highscore);
        if(Contants.User != null){
            for ( Score score:scoreRepository.getScoreForUser(1,Contants.User.getId())) {
                if(score.getLevelHard().getName().toLowerCase().equals("easy")){
                    ((TextView) findViewById(R.id.yourEasyScore)).setText(""+score.getScore());
                } else if(score.getLevelHard().getName().toLowerCase().equals("normal")){
                    ((TextView) findViewById(R.id.yourNormalScore)).setText(""+score.getScore());
                } else {
                    ((TextView) findViewById(R.id.yourDifficultScore)).setText(""+score.getScore());
                }
            }
        }
        findViewById(R.id.highScoreLayout).setBackgroundResource(R.drawable.flappy_bird_backgroup);
        ((ListView) findViewById(R.id.Lst_HighScore)).setAdapter(new ScoreAdapter(scoreModels));
    }

    public void BackToHome(View view){
        setContentView(R.layout.activity_flappy_bird_home);
    }
}