package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.CommonRepository;
import com.project.game.datamanager.DataHelper;
import com.project.game.entity.User;

public class UserRepository implements CommonRepository<User> {
    private Context context;
    private SQLiteDatabase database;
    private UserAchievementRepository achievementRepository;

    public UserRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
        achievementRepository= new UserAchievementRepository(context);
    }

    public User getUser(int userID){
        Cursor cursor = database.rawQuery("SELECT name,avatar FROM user where id = ?",new String[]{userID+""});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            User user = new User(userID, cursor.getString(0), "",cursor.getInt(1));
            user.setAchievements(achievementRepository.get(userID));
            return user;
        }
        return null;
    }

    public User getUser(String accessToken){
        Cursor cursor = database.rawQuery("SELECT id,name,avatar FROM user where accessToken = ?",new String[]{accessToken});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            User user = new User(cursor.getInt(0), cursor.getString(1), accessToken,cursor.getInt(2));
            user.setAchievements(achievementRepository.get(user.getId()));
            return user;
        }
        return null;
    }

    public boolean add(User user){
        try {
            ContentValues contentValues = convertToValue(user);
            database.insert("user",null, contentValues);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean update(User user){
        try {
            ContentValues contentValues = convertToValue(user);
            database.update("user",contentValues, "id = ?", new String[]{user.getId()+""});
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public ContentValues convertToValue(User entity) {
        ContentValues content = new ContentValues();
        content.put("id",entity.getId());
        content.put("name",entity.getName());
        content.put("accessToken",entity.getAccessToken());
        return content;
    }

    public void logout() {
        String removeScore = "DELETE FROM score WHERE userId = 0 ";
        String removeAchievement = "DELETE FROM score WHERE userId = 0";
        String removeUser = "DELETE FROM score WHERE userId = 0";
        database.execSQL(removeScore);
        database.execSQL(removeAchievement);
        database.execSQL(removeUser);
    }
}
