package com.project.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.game.adapter.BoxAdapter;
import com.project.game.gamecontroll.Game2048;

public class Game2048Activity extends AppCompatActivity {
    private GridView gridView;
    private BoxAdapter boxAdapter;
    private View.OnTouchListener listener;
    private float x, y;
    public static TextView txtScore, txtendScore;
    private RelativeLayout overGame2048;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048_home);
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
}