package com.project.game.datamanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.game.datamanager.CommonRepository;
import com.project.game.datamanager.DataHelper;
import com.project.game.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository implements CommonRepository<Question> {
    private Context context;
    private SQLiteDatabase database;
    private UserRepository userRepository;
    private AnswerRepository answerRepository;

    public QuestionRepository(Context context) {
        this.context = context;
        DataHelper databaseHelper = new DataHelper(context);
        database = databaseHelper.getWritableDatabase();
        userRepository = new UserRepository(context);
        answerRepository = new AnswerRepository(context);
    }

    public List<Question> get(int skip, int take){
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id,content,subject,userId FROM question", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String content = cursor.getString(1);
                String subject = cursor.getString(2);
                int userId = cursor.getInt(3);
                Question question = new Question(id,content,subject,userId);
                question.setUser(userRepository.getUser(userId));
                question.setAnswers(answerRepository.getAnswer(id));

                questions.add(question);
            }
        }
        return questions;
    }

    public boolean add(Question question){
        try {
            ContentValues contentValues = convertToValue(question);
            int id = (int) database.insert("question", null, contentValues);
            if(id > 0){
                for (int i=0; i < question.getAnswers().size(); i++){
                    question.getAnswers().get(i).setQuestionId(id);
                    answerRepository.add(question.getAnswers().get(i));
                }
            }
        } catch (Exception ex){
            return false;
        }
        return true;
    }

    @Override
    public ContentValues convertToValue(Question entity) {
        ContentValues content = new ContentValues();
        content.put("id",entity.getId());
        content.put("content",entity.getContent());
        content.put("subject",entity.getSubject());
        content.put("userId",entity.getUserId());
        return content;
    }
}
