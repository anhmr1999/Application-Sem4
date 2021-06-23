package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.DataHelper;
import com.project.game.entity.UserAchievement;

import java.util.ArrayList;
import java.util.List;

public class UserAchievementRepository {
    private Context context;
    private SQLiteDatabase database;
    AchievementRepository achievementRepository;

    public UserAchievementRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
        achievementRepository = new AchievementRepository(context);
    }

    public List<UserAchievement> get(int userId){
        List<UserAchievement> achievements = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT achievementId FROM userachievement WHERE userId = ?",new String[]{userId+""});
        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                UserAchievement userAchievement = new UserAchievement(userId, cursor.getInt(0));
                userAchievement.setAchievement(achievementRepository.findAchievement(userId));
                achievements.add(userAchievement);
            }
        }
        return achievements;
    }

    public boolean add(UserAchievement userAchievement){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", userAchievement.getUserId());
            contentValues.put("achievementId", userAchievement.getAchievementId());
            database.insert("userachievement", null, contentValues);
        } catch (Exception ex){
            return false;
        }
        return true;
    }
}
