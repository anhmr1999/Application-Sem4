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
        Cursor cursor = database.rawQuery("SELECT id,name,tutorial FROM achievement", null);

        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String tutorial = cursor.getString(2);
                achievements.add(new Achievement(id,name,tutorial));
            }
        }
        cursor.close();

        return  achievements;
    }

    public Achievement findAchievement(int id){
        Achievement achievement = null;
        Cursor cursor = database.rawQuery("SELECT name,tutorial FROM achievement WHERE id = ?", new String[]{id+""});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String tutorial = cursor.getString(1);
            achievement = new Achievement(id, name, tutorial);
        }
        cursor.close();

        return achievement;
    }

    @Override
    public ContentValues convertToValue(Achievement entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",entity.getName());
        contentValues.put("tutorial",entity.getTutorial());
        return contentValues;
    }
}
