package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.CommonRepository;
import com.project.game.datamanager.DataHelper;
import com.project.game.entity.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerRepository implements CommonRepository<Answer> {
    private Context context;
    private SQLiteDatabase database;

    public AnswerRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public List<Answer> getAnswer(int questionId){
        List<Answer> answers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,content,correct,questionId FROM answer WHERE questionId = ?", new String[]{questionId+""});
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                boolean correct = cursor.getInt(2) == 1 ? true : false;
                answers.add(new Answer(id,content,correct,questionId));
            }
        }
        cursor.close();
        return answers;
    }

    public boolean add(Answer answer){
        try {
            ContentValues content = convertToValue(answer);
            database.insert("answer", null, content);
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public ContentValues convertToValue(Answer entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", entity.getId());
        contentValues.put("content", entity.getContent());
        contentValues.put("correct", entity.isCorrect());
        contentValues.put("questionId", entity.getQuestionId());
        return contentValues;
    }
}
