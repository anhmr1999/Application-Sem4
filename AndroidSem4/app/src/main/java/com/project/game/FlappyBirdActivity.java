package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.game.adapter.LevelAdapter;
import com.project.game.datamanager.repository.LevelHardRepository;
import com.project.game.datamanager.repository.ScoreRepository;

public class FlappyBirdActivity extends AppCompatActivity {
    public static TextView txtScore, endScore;
    public static RelativeLayout endgameLayout;
    private MediaPlayer mediaPlayer;
    private LevelHardRepository levelHardRepository;
    private SharedPreferences sp;
    private int levelId;
    private ScoreRepository scoreRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flappy_bird_home);
        levelHardRepository = new LevelHardRepository(FlappyBirdActivity.this);
        scoreRepository = new ScoreRepository(FlappyBirdActivity.this);
        sp = FlappyBirdActivity.this.getSharedPreferences("flappyBirdSetting", Context.MODE_PRIVATE);
        if(sp!=null){
            levelId = sp.getInt("levelId",0);
        } else {
            levelId = levelHardRepository.getLevelGame(1).get(0).getId();
            sp = FlappyBirdActivity.this.getSharedPreferences("flappyBirdSetting", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("levelId",levelHardRepository.getLevelGame(1).get(0).getId());
            editor.apply();
        }
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
                setContentView(R.layout.activity_flappy_bird_home);
            }
        });
    }

    public void viewScore(View view){

    }
}