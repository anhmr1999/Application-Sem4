package com.project.game.gamecontroll;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.project.game.KnowledgeActivity;
import com.project.game.R;
import com.project.game.adapter.AnswerAdapter;
import com.project.game.datamanager.repository.QuestionRepository;
import com.project.game.entity.Answer;
import com.project.game.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knowledge {
    private QuestionRepository repository;
    private static Knowledge knowledge;
    private List<Question> questionList;
    private int score = 0;
    private Random random;

    static {knowledge = new Knowledge();}

    public static Knowledge getDataGame() {
        return knowledge;
    }

    public void init(Context context){
        random = new Random();
        score = 0;
        repository = new QuestionRepository(context);
        getQuestion();
    }

    public void changeQuestion(){
        Question question;
        if(questionList.size() > 1){
            question = questionList.get(random.nextInt(questionList.size() - 1));
            questionList.remove(question);
        } else {
            question = questionList.get(0);
        }
        KnowledgeActivity.txt_Question.setText(question.getContent());
        KnowledgeActivity.lst_answer.setAdapter(new AnswerAdapter(question.getAnswers()));
    }

    public void CorrectAnswer(){
        score += 5;
        KnowledgeActivity.txt_Score.setText(""+score);
    }

    public void EndGame() {
        KnowledgeActivity.txt_EndCore.setText(""+score);
        KnowledgeActivity.GameKnowLedgeOver.setVisibility(View.VISIBLE);
    }

    private void getQuestion(){
        questionList = repository.get(0, 20);
        Log.e("ErrorQuestion",""+questionList.size());
    }
}
