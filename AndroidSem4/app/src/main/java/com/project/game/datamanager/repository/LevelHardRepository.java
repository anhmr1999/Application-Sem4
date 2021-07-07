package com.project.game.datamanager.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.DataHelper;
import com.project.game.entity.LevelHard;

import java.util.ArrayList;
import java.util.List;

public class LevelHardRepository {
    private Context context;
    private SQLiteDatabase database;

    public LevelHardRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public List<LevelHard> getLevelGame(){
        List<LevelHard> levelHards = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,name,description FROM levelHard", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                levelHards.add(new LevelHard(id,name,description));
            }
        }
        return levelHards;
    }

    public LevelHard getLevel(int levelId){
        LevelHard level = null;
        Cursor cursor = database.rawQuery("SELECT id,name,description FROM levelHard WHERE id = ?", new String[]{levelId+""});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            level = new LevelHard(id,name,description);
        }
        return level;
    }
}
