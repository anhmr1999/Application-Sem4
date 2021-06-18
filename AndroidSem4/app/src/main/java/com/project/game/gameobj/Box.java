package com.project.game.gameobj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.project.game.gamecontroll.Game2048;

@SuppressLint("AppCompatCustomView")
public class Box extends TextView {
    public Box(Context context) {
        super(context);
    }

    public Box(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Box(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredWidth();
        setMeasuredDimension(height,height);
    }

    public void setTextBox(int number){
        if(number < 100){
            setTextSize(30);
        } else if(number < 1000){
            setTextSize(25);
        } else {
            setTextSize(20);
        }

        if(number <= 16){
            setTextColor(Color.BLACK);
        } else if(number < 16000) {
            setTextColor(Color.WHITE);
        } else {
            setTextColor(Color.RED);
        }

        this.setBackgroundColor(Game2048.getDataGame().getPositionColor(number));

        if(number == 0){
            setText("");
        } else {
            setText("" + number);
        }
    }
}
