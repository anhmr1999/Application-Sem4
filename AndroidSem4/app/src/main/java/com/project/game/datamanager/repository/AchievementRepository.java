package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.CommonRepository;
import com.project.game.datamanager.DataHelper;
import com.project.game.entity.Achievement;

import java.util.ArrayList;
import java.util.List;

public class AchievementRepository implements CommonRepository<Achievement> {
    private Context context;
    private SQLiteDatabase database;

    public AchievementRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public List<Achievement> getAchievement(){
        List<Achievement> achievements = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,name,tutorial,checkScore,scoreOrNumber, level FROM achievement", null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String tutorial = cursor.getString(2);
                boolean checkScore = cursor.getInt(3) == 1;
                int scoreOrNumber = cursor.getInt(4);
                String level = cursor.getString(5);
                achievements.add(new Achievement(id,name,tutorial, checkScore, scoreOrNumber,level));
            }
        }
        cursor.close();

        return  achievements;
    }

    public List<Achievement> getAchievement(int gameId){
        List<Achievement> achievements = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,name,tutorial,checkScore,scoreOrNumber,level FROM achievement WHERE gameId = ? AND id NOT IN (SELECT achievementId FROM userachievement)", new String[]{gameId+""});

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String tutorial = cursor.getString(2);
                boolean checkScore = cursor.getInt(3) == 1;
                int scoreOrNumber = cursor.getInt(4);
                String level = cursor.getString(5);
                achievements.add(new Achievement(id,name,tutorial, checkScore, scoreOrNumber,level));
            }
        }
        cursor.close();

        return  achievements;
    }

    public Achievement findAchievement(int id){
        Achievement achievement = null;
        Cursor cursor = database.rawQuery("SELECT id,name,tutorial,checkScore,scoreOrNumber,level FROM achievement WHERE id = ?", new String[]{id+""});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            id = cursor.getInt(0);
            String name = cursor.getString(1);
            String tutorial = cursor.getString(2);
            boolean checkScore = cursor.getInt(3) == 1;
            int scoreOrNumber = cursor.getInt(4);
            String level = cursor.getString(5);
            achievement = new Achievement(id,name,tutorial, checkScore, scoreOrNumber,level);
        }
        cursor.close();

        return achievement;
    }

    public Achievement findAchievement(String name){
        Achievement achievement = null;
        Cursor cursor = database.rawQuery("SELECT id,name,tutorial,checkScore,scoreOrNumber,level FROM achievement WHERE name = ?", new String[]{name});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            name = cursor.getString(1);
            String tutorial = cursor.getString(2);
            boolean checkScore = cursor.getInt(3) == 1;
            int scoreOrNumber = cursor.getInt(4);
            String level = cursor.getString(5);
            achievement = new Achievement(id,name,tutorial, checkScore, scoreOrNumber,level);
        }
        cursor.close();

        return achievement;
    }

    public int getLastId(){
        Cursor cursor = database.rawQuery("SELECT MAX(id)  FROM achievement", null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public boolean addAchievement(Achievement achievement){
        try {
            ContentValues values = convertToValue(achievement);
            database.insert("answer", null, values);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public ContentValues convertToValue(Achievement entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",entity.getName());
        contentValues.put("tutorial",entity.getTutorial());
        return contentValues;
    }
}
