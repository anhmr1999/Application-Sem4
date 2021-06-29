package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.adapter.BoxAdapter;
import com.project.game.adapter.LevelAdapter;
import com.project.game.adapter.ScoreAdapter;
import com.project.game.adapter.ScoreModel;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.entity.LevelHard;
import com.project.game.entity.Score;
import com.project.game.gamecontroll.Game2048;

import java.util.ArrayList;
import java.util.List;

public class Game2048Activity extends AppCompatActivity {
    private GridView gridView;
    private BoxAdapter boxAdapter;
    private View.OnTouchListener listener;
    private LevelHardRepository levelHardRepository;
    private ScoreRepository scoreRepository;
    private float x, y;
    private SharedPreferences sp;
    private int levelId;
    public static TextView txtScore, txtendScore;
    private RelativeLayout overGame2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game2048_home);
        levelHardRepository = new LevelHardRepository(Game2048Activity.this);
        scoreRepository = new ScoreRepository(Game2048Activity.this);
        sp = Game2048Activity.this.getSharedPreferences("game2048Setting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        }
        if(levelId == 0){
            levelId = levelHardRepository.getLevelGame(2).get(0).getId();
            sp = Game2048Activity.this.getSharedPreferences("game2048Setting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelId);
            editor.apply();
        }
        Contants._2048Level = levelHardRepository.getLevel(levelId);
    }

    public void Play2048(View view){
        setContentView(R.layout.activity_game2048);
        txtScore = findViewById(R.id.txt2048Score);
        txtendScore = findViewById(R.id.game2048EndScore);
        gridView = (GridView) findViewById(R.id.gridView);
        overGame2048 = findViewById(R.id.Game2048Over);
        init();
    }

    private void init(){
        Game2048.getDataGame().init(Game2048Activity.this);
        boxAdapter = new BoxAdapter(Game2048Activity.this, 0, Game2048.getDataGame().getArrView());

        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(Math.abs(event.getX() - x) > Math.abs(event.getY() - y)){
                            if(event.getX() > x){
                                Game2048.getDataGame().leftToRight();
                            } else if(event.getX() < x) {
                                Game2048.getDataGame().rightToLeft();
                            } else {}
                        } else {
                            if(event.getY() > y){
                                Game2048.getDataGame().topToBottom();
                            } else if(event.getY() < y) {
                                Game2048.getDataGame().bottomToTop();
                            } else {}
                        }
                        boxAdapter.notifyDataSetChanged();
                }

                if(!Game2048.getDataGame().canMove()){
                    overGame2048.setVisibility(View.VISIBLE);
                    txtendScore.setText("" + Game2048.getDataGame().getScore());
                } else {
                    txtScore.setText("" + Game2048.getDataGame().getScore());
                }
                return true;
            }
        };

        overGame2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_game2048_home);
            }
        });

        gridView.setAdapter(boxAdapter);
        gridView.setOnTouchListener(listener);
    }

    public void ChangeLevel(View view){
        setContentView(R.layout.activity_level);
        findViewById(R.id.levelLayout).setBackgroundResource(R.drawable.background_2048);
        ((ListView)findViewById(R.id.Lst_Level)).setAdapter(new LevelAdapter(levelHardRepository.getLevelGame(2), levelId));
        ((ListView)findViewById(R.id.Lst_Level)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                levelId = (int) ((ListView) findViewById(R.id.Lst_Level)).getAdapter().getItemId(position);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("levelId",levelId);
                editor.apply();
                Contants._2048Level = levelHardRepository.getLevel(levelId);
                setContentView(R.layout.activity_game2048_home);
            }
        });
    }

    public void viewScore(View view){
        List<ScoreModel> scoreModels = new ArrayList<>();
        for (Score score : scoreRepository.GetScore(2)) {
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
        if(Contants.user != null){
            for ( Score score:scoreRepository.getScoreForUser(2,Contants.user.getId())) {
                if(score.getLevelHard().getName().toLowerCase().equals("easy")){
                    ((TextView) findViewById(R.id.yourEasyScore)).setText(""+score.getScore());
                } else if(score.getLevelHard().getName().toLowerCase().equals("normal")){
                    ((TextView) findViewById(R.id.yourNormalScore)).setText(""+score.getScore());
                } else {
                    ((TextView) findViewById(R.id.yourDifficultScore)).setText(""+score.getScore());
                }
            }
        }
        findViewById(R.id.highScoreLayout).setBackgroundResource(R.drawable.background_2048);
        ((ListView) findViewById(R.id.Lst_HighScore)).setAdapter(new ScoreAdapter(scoreModels));
    }

    public void BackToHome(View view){
        setContentView(R.layout.activity_game2048_home);
    }
}