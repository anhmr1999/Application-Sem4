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

    public List<LevelHard> getLevelGame(int gameId){
        List<LevelHard> levelHards = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,name,description,gameId FROM levelHard WHERE gameId = ?", new String[]{gameId+""});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(3);
                levelHards.add(new LevelHard(id,name,description,gameId));
            }
        }
        return levelHards;
    }
}
