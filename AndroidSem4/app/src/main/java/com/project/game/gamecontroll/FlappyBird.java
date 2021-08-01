package com.project.game.gamecontroll;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.project.game.FlappyBirdActivity;
import com.project.game.R;
import com.project.game.common.ApiProviderImpl;
import com.project.game.common.Contants;
import com.project.game.datamanager.repository.AchievementRepository;
import com.project.game.datamanager.repository.ScoreRepository;
import com.project.game.datamanager.repository.UserAchievementRepository;
import com.project.game.entity.Achievement;
import com.project.game.entity.Score;
import com.project.game.entity.UserAchievement;
import com.project.game.gameobj.Bird;
import com.project.game.gameobj.Pipe;

import java.util.ArrayList;
import java.util.List;

public class FlappyBird extends View {
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private int sumpipe, distance;
    private ArrayList<Pipe> arrPipes;
    private int score,bestScore = 0;
    private Context context;
    private int soundJump;
    private boolean loadSound, showAchievement;
    private SoundPool soundPool;
    public static boolean gameover;
    private AchievementRepository achievementRepository;
    private ScoreRepository scoreRepository;
    private UserAchievementRepository userAchievementRepository;
    private ApiProviderImpl apiProvider;

    public FlappyBird(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        apiProvider = new ApiProviderImpl(context);
        achievementRepository = new AchievementRepository(context);
        userAchievementRepository = new UserAchievementRepository(context);
        scoreRepository = new ScoreRepository(context);
        SharedPreferences sp = context.getSharedPreferences("gameSetting",Context.MODE_PRIVATE);
        if(sp!=null){
            bestScore = sp.getInt("bestScore",0);
        }
        gameover = false;
        showAchievement = false;
        initBird();
        initPipe();
        score = 0;
        handler = new Handler();
        r= new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        if(Build.VERSION.SDK_INT>=21){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            this.soundPool = builder.build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loadSound = true;
            }
        });
        soundJump = this.soundPool.load(context,R.raw.jump_02,1);
    }

    private void initBird(){
        bird = new Bird();
        bird.setWidth(100* Contants.Screen_Width/1080);
        bird.setHeight(100*Contants.Screen_Height/1920);
        bird.setX(100*Contants.Screen_Width/1080);
        bird.setY(Contants.Screen_Height/2 - bird.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird_fly));
        bird.setArrBms(arrBms);
    }

    private void initPipe(){
        sumpipe = 4;
        if(Contants.flappyBirdLevel.getName().toLowerCase().equals("easy")){
            distance = 450*Contants.Screen_Height/1920;
        } else if(Contants.flappyBirdLevel.getName().toLowerCase().equals("normal")) {
            distance = 400*Contants.Screen_Height/1920;
        } else {
            distance = 350*Contants.Screen_Height/1920;
        }
        arrPipes = new ArrayList<>();
        for (int i = 0; i< sumpipe; i++){
            if (i < sumpipe /2 ){
                this.arrPipes.add(new Pipe(Contants.Screen_Width + i*((Contants.Screen_Width + 200*Contants.Screen_Width/1080)/(sumpipe/2)),
                        0, 200*Contants.Screen_Width/1080,Contants.Screen_Height/2));
                this.arrPipes.get(this.arrPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe_top));
                this.arrPipes.get(this.arrPipes.size() - 1).randomY();
            } else {
                this.arrPipes.add(new Pipe(this.arrPipes.get(i-sumpipe/2).getX(), this.arrPipes.get(i-sumpipe/2).getY()
                        + this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance, 200*Contants.Screen_Width/1080, Contants.Screen_Height/2));
                this.arrPipes.get(this.arrPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe_bottom));
            }
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        bird.draw(canvas);
        for (int i = 0; i < sumpipe; i++) {
            if(bird.getRect().intersect(arrPipes.get(i).getRect())||bird.getY()-bird.getHeight()<0||bird.getY() > Contants.Screen_Height || gameover){
                Pipe.speed = 0;
                FlappyBirdActivity.endgameLayout.setVisibility(VISIBLE);
                FlappyBirdActivity.endScore.setText(FlappyBirdActivity.txtScore.getText());
                FlappyBirdActivity.isPlaygame = false;
                if(!showAchievement){
                    checkAchievement();
                }
            }

            if (this.bird.getX() + this.bird.getWidth() > arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2
                    && this.bird.getX() + this.bird.getWidth() <= arrPipes.get(i).getX() + arrPipes.get(i).getWidth() / 2 + Pipe.speed
                    && i < sumpipe / 2) {
                score++;
                if(score > bestScore){
                    bestScore = score;
                    SharedPreferences sp = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("bestScore",bestScore);
                    editor.apply();
                }
                FlappyBirdActivity.txtScore.setText("" + score);
            }

            if (this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()) {
                this.arrPipes.get(i).setX(Contants.Screen_Width);
                if (i < sumpipe / 2) {
                    arrPipes.get(i).randomY();
                } else {
                    arrPipes.get(i).setY(this.arrPipes.get(i - sumpipe / 2).getY()
                            + this.arrPipes.get(i - sumpipe / 2).getHeight() + this.distance);
                }
            }
            this.arrPipes.get(i).draw(canvas);
        }
        handler.postDelayed(r, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            bird.setDrop(-15);
            if(loadSound && Contants.Music){
                int streamId = this.soundPool.play(this.soundJump, (float)0.5, (float)0.5,1,0,1);
            }
        }
        return true;
    }

    private void checkAchievement(){
        showAchievement = true;

        Score currentScore = scoreRepository.getScoreForUpdate(1, Contants.User.getId(), Contants.flappyBirdLevel.getId());
        if(currentScore == null){
            currentScore = new Score(Contants.flappyBirdLevel.getId(),1, Contants.User.getId(), score, false);
            if(scoreRepository.add(currentScore)){
                if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                    apiProvider.UpdateScore();
                }
            }
        } else {
            if(currentScore.getScore() < score){
                currentScore.setScore(score);
                currentScore.setUpload(false);
                if(scoreRepository.update(currentScore)){
                    if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                        apiProvider.UpdateScore();
                    }
                }
            }
        }

        List<Achievement> achievements = new ArrayList<>();
        for (Achievement achievement: achievementRepository.getAchievement(1)) {
            if(achievement.getScoreOrNumber() <= score && achievement.getLevelName().equals(Contants.flappyBirdLevel.getName())){
                achievements.add(achievement);
                userAchievementRepository.add(new UserAchievement(Contants.User.getId(), achievement.getId(), false));
            }
        }
        if(achievements.size() > 0){
            FlappyBirdActivity.dialog.setAchievement(achievements);
            FlappyBirdActivity.dialog.show();
            if(Contants.User.getAccessToken() != null && Contants.IsNetworkConnected(context) && Contants.User.getAccessToken() != ""){
                apiProvider.UpdateAchievement();
            }
        }
    }
}
