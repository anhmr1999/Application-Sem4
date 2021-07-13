package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.adapter.AchievementDialog;
import com.project.game.adapter.BoxAdapter;
import com.project.game.adapter.LevelAdapter;
import com.project.game.adapter.ScoreAdapter;
import com.project.game.adapter.ScoreModel;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.entity.Achievement;
import com.project.game.entity.LevelHard;
import com.project.game.entity.Score;
import com.project.game.entity.UserAchievement;
import com.project.game.gamecontroll.Game2048;

import java.util.ArrayList;
import java.util.List;

public class Game2048Activity extends AppCompatActivity {
    private GridView gridView;
    private BoxAdapter boxAdapter;
    private View.OnTouchListener listener;
    private LevelHardRepository levelHardRepository;
    private ScoreRepository scoreRepository;
    private AchievementRepository achievementRepository;
    private UserAchievementRepository userAchievementRepository;
    private float x, y;
    private SharedPreferences sp;
    private AchievementDialog dialog;
    private int levelId;
    public static TextView txtScore, txtendScore;
    private RelativeLayout overGame2048;
    private boolean allowBack, isPlayGame, gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allowBack = true;
        isPlayGame = gameOver = false;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game2048_home);
        levelHardRepository = new LevelHardRepository(Game2048Activity.this);
        scoreRepository = new ScoreRepository(Game2048Activity.this);
        achievementRepository = new AchievementRepository(Game2048Activity.this);
        userAchievementRepository = new UserAchievementRepository(Game2048Activity.this);
        sp = Game2048Activity.this.getSharedPreferences("game2048Setting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        }
        if(levelId == 0){
            levelId = levelHardRepository.getLevelGame().get(0).getId();
            sp = Game2048Activity.this.getSharedPreferences("game2048Setting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelId);
            editor.apply();
        }
        Contants._2048Level = levelHardRepository.getLevel(levelId);
        dialog = new AchievementDialog(Game2048Activity.this,null);
    }

    public void Play2048(View view){
        allowBack = gameOver = false;
        isPlayGame = true;
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
                    checkScore();
                    gameOver = true;
                    isPlayGame = false;
                } else {
                    txtScore.setText("" + Game2048.getDataGame().getScore());
                }
                return true;
            }
        };

        overGame2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(1000, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        setContentView(R.layout.activity_game2048_home);
                        allowBack = true;
                    }
                }.start();
            }
        });

        gridView.setAdapter(boxAdapter);
        gridView.setOnTouchListener(listener);
    }

    public void ChangeLevel(View view){
        allowBack = false;
        setContentView(R.layout.activity_level);
        findViewById(R.id.levelLayout).setBackgroundResource(R.drawable.background_2048);
        ((ListView)findViewById(R.id.Lst_Level)).setAdapter(new LevelAdapter(levelHardRepository.getLevelGame(), levelId));
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

    private void checkScore(){
        Score currentScore = scoreRepository.getScoreForUpdate(1, Contants.User.getId(), Contants._2048Level.getId());
        if(currentScore == null){
            currentScore = new Score(Contants._2048Level.getId(),1, Contants.User.getId(), Game2048.getDataGame().getScore(), false);
            scoreRepository.add(currentScore);
        } else {
            if(currentScore.getScore() < Game2048.getDataGame().getScore()){
                currentScore.setScore(Game2048.getDataGame().getScore());
                currentScore.setUpload(false);
                scoreRepository.update(currentScore);
            }
        }

        List<Achievement> achievements = new ArrayList<>();
        for (Achievement achievement: achievementRepository.getAchievement(2)) {
            if(achievement.isCheckScore()){
                if(achievement.getScoreOrNumber() <= Game2048.getDataGame().getScore() && achievement.getLevelName().equals(Contants._2048Level.getName())){
                    achievements.add(achievement);
                    userAchievementRepository.add(new UserAchievement(Contants.User.getId(), achievement.getId(), false));
                }
            } else {
                for (Integer number : Game2048.getDataGame().getArrView()){
                    if(number >= achievement.getScoreOrNumber()){
                        achievements.add(achievement);
                        userAchievementRepository.add(new UserAchievement(Contants.User.getId(), achievement.getId(), false));
                    }
                }
            }
        }
        if(achievements.size() > 0){
            dialog.setAchievement(achievements);
            dialog.show();
        }
    }

    public void viewScore(View view){
        allowBack = false;
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
        if(Contants.User != null){
            for ( Score score:scoreRepository.getScoreForUser(2,Contants.User.getId())) {
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
                    setContentView(R.layout.activity_game2048_home);
                    allowBack = !allowBack;
                } else {
                    overGame2048.setVisibility(View.VISIBLE);
                    txtendScore.setText("" + Game2048.getDataGame().getScore());
                    gameOver = true;
                }
            } else {
                setContentView(R.layout.activity_game2048_home);
                allowBack = !allowBack;
            }
        }
    }
}