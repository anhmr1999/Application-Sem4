package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.CommonRepository;
import com.project.game.datamanager.DataHelper;
import com.project.game.entity.Score;
import com.project.game.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ScoreRepository implements CommonRepository<Score> {
    private Context context;
    private SQLiteDatabase database;
    private UserRepository userRepository;
    private LevelHardRepository levelHardRepository;

    public ScoreRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
        userRepository = new UserRepository(context);
        levelHardRepository = new LevelHardRepository(context);
    }

    public List<Score> GetScore(){
        List<Score> scores = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT gameId,levelId,userId,score FROM score", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int gameId = cursor.getInt(0);
                int levelId = cursor.getInt(1);
                int userId = cursor.getInt(2);
                int scoreNumber = cursor.getInt(3);
                Score score = new Score(gameId,userId,levelId,scoreNumber);
                score.setUser(userRepository.getUser(userId));
                score.setLevelHard(levelHardRepository.getLevel(levelId));
                scores.add(score);
            }
        }

        cursor.close();
        return scores;
    }

    public List<Score> GetScore(int gameId){
        List<Score> scores = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT gameId,levelId,userId,score FROM score WHERE gameId = ?", new String[]{gameId+""});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int levelId = cursor.getInt(1);
                int userId = cursor.getInt(2);
                int scoreNumber = cursor.getInt(3);
                Score score = new Score(gameId,userId,levelId,scoreNumber);
                score.setUser(userRepository.getUser(userId));
                score.setLevelHard(levelHardRepository.getLevel(levelId));
                scores.add(score);
            }
        }

        cursor.close();
        return scores;
    }

    public List<Score> getScoreForUser(int gameId, int userId){
        List<Score> scores = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT score, levelId FROM score WHERE gameId = ? AND userId = ?"
                , new String[]{gameId+"", userId+""});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Score score = new Score(gameId,userId,cursor.getInt(1),cursor.getInt(0));
                score.setLevelHard(levelHardRepository.getLevel(score.getLevelHardId()));
                scores.add(score);
            }
        }
        cursor.close();
        return scores;
    }

    public List<Score> getListScoreUpload() {
        List<Score> scores = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT score,gameId, levelId,userId FROM score WHERE isUpload = 0", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int scoreNumber = cursor.getInt(0);
                int gameId = cursor.getInt(1);
                int levelId = cursor.getInt(2);
                int userId = cursor.getInt(3);
                Score score = new Score(gameId,userId,levelId,scoreNumber);
                scores.add(score);
            }
        }
        cursor.close();
        return scores;
    }

    public void UploadSuccess(int userId, int gameId, int levelId){
        database.execSQL("UPDATE score SET isUpload = 1 WHERE gameId = ? AND userId = ? AND levelId = ?", new String[]{gameId+"", userId+"", levelId+""});
    }

    public Score getScoreForUpdate(int gameId, int userId, int levelId){
        Score score = null;
        Cursor cursor = database.rawQuery("SELECT score FROM score WHERE gameId = ? AND userId = ? AND levelId = ?"
                , new String[]{gameId+"", userId+"", levelId+""});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            score = new Score(gameId,userId,levelId,cursor.getInt(0));
        }
        cursor.close();
        return score;
    }

    public boolean add(Score score){
        try {
            ContentValues contentValues = convertToValue(score);
            database.insert("score",null, contentValues);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean update(Score score){
        try {
            ContentValues contentValues = convertToValue(score);
            database.update("score", contentValues,"gameId = ? AND userId = ? AND levelId = ?"
                    ,new String[]{score.getGameId()+"", score.getUserId()+"", score.getLevelHardId()+""});
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public ContentValues convertToValue(Score entity) {
        ContentValues content = new ContentValues();
        content.put("gameId", entity.getGameId());
        content.put("userId", entity.getUserId());
        content.put("levelId", entity.getLevelHardId());
        content.put("isUpload", false);
        content.put("score", entity.getScore());
        return content;
    }
}
