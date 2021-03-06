package com.project.game.gameobj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.project.game.common.Contants;

import java.util.Random;

public class Pipe extends BaseObject{
    public static int speed;
    public Pipe(float x, float y, int width, int height) {
        super(x, y, width, height);
        speed = 10* Contants.Screen_Width/1080;
    }

    public void draw(Canvas canvas){
        this.x-=speed;
        canvas.drawBitmap(this.bm, this.x, this.y, null);
    }

    public void randomY(){
        Random random = new Random();
        this.y = random.nextInt((0+this.height/4) +1 ) - this.height/4;
    }

    @Override
    public void setBm(Bitmap bm){
        this.bm = Bitmap.createScaledBitmap(bm, width, height, true);

    }
}