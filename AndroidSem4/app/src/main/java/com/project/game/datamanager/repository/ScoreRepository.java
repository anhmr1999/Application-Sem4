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

    public ScoreRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
        userRepository = new UserRepository(context);
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
                scores.add(score);
            }
        }

        cursor.close();
        return scores;
    }

    public Score getScoreForUser(int gameId, int userId, int levelId){
        Score score = null;
        Cursor cursor = database.rawQuery("SELECT score FROM score WHERE gameId = ? AND levelId = ? AND userId = ?"
                , new String[]{gameId+"", levelId+"", userId+""});
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
        content.put("score", entity.getScore());
        return content;
    }
}
