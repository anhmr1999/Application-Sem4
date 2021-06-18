package com.project.game.gamecontroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.project.game.R;

import java.util.ArrayList;
import java.util.Random;

public class Game2048 {
    private static Game2048 game2048;
    private ArrayList<Integer> arrView = new ArrayList<>();
    private int[][] arrNumber = new int[4][4];
    private int[] arrColor;
    private Random random = new Random();
    private int score = 0;

    static {game2048 = new Game2048();}

    public static Game2048 getDataGame() {
        return game2048;
    }

    public void init(Context context){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                arrNumber[i][j] = 0;
                arrView.add(0);
            }
        }
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.boxBackgroundColor);
        arrColor = new int[typedArray.length()];
        for (int i=0; i<typedArray.length(); i++){
            arrColor[i] = typedArray.getColor(i,0);
        }
        typedArray.recycle();
        createNumber();
        reload();
    }

    public ArrayList<Integer> getArrView() {
        return arrView;
    }

    public int getPositionColor(int number){
        if(number == 0){
            return Color.WHITE;
        } else {
            int position = (int) (Math.log(number)/Math.log(2)) - 1;
            return arrColor[position];
        }
    }

    public void createNumber(){
        int emptyBox = 0;
        for (int i=0; i< arrView.size(); i++){
            if(arrView.get(i) == 0){
                emptyBox++;
            }
        }
        int numberCreated;
        if(emptyBox == 1){
            numberCreated=1;
        } else if(emptyBox > 1){
            numberCreated = random.nextInt(2)+1;
        } else {
            numberCreated = 0;
        }
        while (numberCreated != 0){
            int i = random.nextInt(4), j = random.nextInt(4);
            if(arrNumber[i][j] == 0){
                arrNumber[i][j] =2;
                numberCreated--;
            }
        }
    }

    public void reload(){
        arrView.clear();
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                arrView.add(arrNumber[i][j]);
            }
        }
    }

    public int getScore(){
        return score;
    }

    public boolean canMove(){
        if(canMoveHorizontal() || canMoveVertical()){
            return true;
        }
        return false;
    }

    private boolean canMoveHorizontal(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                if(arrNumber[i][j] == 0){
                    return true;
                } else {
                    int so = arrNumber[i][j];
                    for (int k=j+1; k<4; k++){
                        if(arrNumber[i][k] == 0){
                            return true;
                        } else {
                            if(so == arrNumber[i][k]){
                                return true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canMoveVertical(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                if(arrNumber[j][i] == 0){
                    return true;
                } else {
                    int so = arrNumber[j][i];
                    for (int k=j+1; k<4; k++){
                        if(arrNumber[k][i] == 0){
                            return true;
                        } else {
                            if(so == arrNumber[k][i]){
                                return true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void rightToLeft(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                int so = arrNumber[i][j];
                if(so == 0){
                    continue;
                } else {
                    for (int k=j+1; k<4; k++){
                        int sox = arrNumber[i][k];
                        if(sox == 0){
                            continue;
                        } else {
                            if(sox == so){
                                arrNumber[i][j] = so*2;
                                arrNumber[i][k] = 0;
                                score+=so*2;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                int so = arrNumber[i][j];
                if(so == 0){
                    for (int k=j+1; k<4; k++){
                        int sox = arrNumber[i][k];
                        if(sox == 0){
                            continue;
                        } else {
                            arrNumber[i][j] = arrNumber[i][k];
                            arrNumber[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }

        createNumber();
        reload();
    }
    public void leftToRight(){
        for (int i=3; i>=0; i--){
            for (int j=3; j>=0; j--){
                int so = arrNumber[i][j];
                if(so == 0){
                    continue;
                } else {
                    for (int k=j-1; k>=0; k--){
                        int sox = arrNumber[i][k];
                        if(sox == 0){
                            continue;
                        } else {
                            if(sox == so){
                                arrNumber[i][j] = so*2;
                                arrNumber[i][k] = 0;
                                score+=so*2;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (int i=3; i>=0; i--){
            for (int j=3; j>=0; j--){
                int so = arrNumber[i][j];
                if(so == 0){
                    for (int k=j-1; k>=0; k--){
                        int sox = arrNumber[i][k];
                        if(sox == 0){
                            continue;
                        } else {
                            arrNumber[i][j] = arrNumber[i][k];
                            arrNumber[i][k] = 0;
                            break;
                        }
                    }
                }
            }
        }

        createNumber();
        reload();
    }
    public void bottomToTop(){
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                int so = arrNumber[j][i];
                if(so == 0){
                    continue;
                } else {
                    for (int k=j+1; k<4; k++){
                        int sox = arrNumber[k][i];
                        if(sox == 0){
                            continue;
                        } else {
                            if(sox == so){
                                arrNumber[j][i] = so*2;
                                arrNumber[k][i] = 0;
                                score+=so*2;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                int so = arrNumber[j][i];
                if(so == 0){
                    for (int k=j+1; k<4; k++){
                        int sox = arrNumber[k][i];
                        if(sox == 0){
                            continue;
                        } else {
                            arrNumber[j][i] = arrNumber[k][i];
                            arrNumber[k][i] = 0;
                            break;
                        }
                    }
                }
            }
        }

        createNumber();
        reload();
    }
    public void topToBottom(){
        for (int i=3; i>=0; i--){
            for (int j=3; j>=0; j--){
                int so = arrNumber[j][i];
                if(so == 0){
                    continue;
                } else {
                    for (int k=j-1; k>=0; k--){
                        int sox = arrNumber[k][i];
                        if(sox == 0){
                            continue;
                        } else {
                            if(sox == so){
                                arrNumber[j][i] = so*2;
                                arrNumber[k][i] = 0;
                                score+=so*2;
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        for (int i=3; i>=0; i--){
            for (int j=3; j>=0; j--){
                int so = arrNumber[j][i];
                if(so == 0){
                    for (int k=j-1; k>=0; k--){
                        int sox = arrNumber[k][i];
                        if(sox == 0){
                            continue;
                        } else {
                            arrNumber[j][i] = arrNumber[k][i];
                            arrNumber[k][i] = 0;
                            break;
                        }
                    }
                }
            }
        }

        createNumber();
        reload();
    }
}
