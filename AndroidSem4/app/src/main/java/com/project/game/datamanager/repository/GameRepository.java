package com.project.game.datamanager.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.DataHelper;
import com.project.game.entity.Game;

public class GameRepository {
    private Context context;
    private SQLiteDatabase database;

    public GameRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
    }
}
