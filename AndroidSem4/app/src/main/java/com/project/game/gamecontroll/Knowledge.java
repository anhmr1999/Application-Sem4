package com.project.game.gamecontroll;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.project.game.KnowledgeActivity;
import com.project.game.R;
import com.project.game.adapter.AnswerAdapter;
import com.project.game.entity.Answer;
import com.project.game.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knowledge {
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
        getQuestion();
    }

    public Question changeQuestion(){
        Question question = questionList.get(random.nextInt(questionList.size() - 1));
        return question;
    }

    private void getQuestion(){
        questionList = new ArrayList<>();
        Question question;
        List<Answer> answers;
        Answer answer;

        question = new Question();
        question.setContent("Haiku là thể thơ truyền thống của nước nào?");
        answers = new ArrayList<>();
        answer = new Answer();
        answer.setContent("Nhật bản");
        answer.setCorrect(true);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Mông Cổ");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Hàn Quốc");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Trung Quốc");
        answer.setCorrect(false);
        answers.add(answer);
        question.setAnswers(answers);
        questionList.add(question);

        question = new Question();
        question.setContent("Đâu là tên một bãi biển ở Quảng Bình?");
        answers = new ArrayList<>();
        answer = new Answer();
        answer.setContent("Đá Nhảy");
        answer.setCorrect(true);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Đá Bò");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Đá Chạy");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Đá Lăn");
        answer.setCorrect(false);
        answers.add(answer);
        question.setAnswers(answers);
        questionList.add(question);

        question = new Question();
        question.setContent("Đâu là một loại hình chợ tạm tự phát thường xuất hiện trong các khu dân cư?");
        answers = new ArrayList<>();
        answer = new Answer();
        answer.setContent("Nhái");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Thằn lằn");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Ếch");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Cóc");
        answer.setCorrect(true);
        answers.add(answer);
        question.setAnswers(answers);
        questionList.add(question);

        question = new Question();
        question.setContent("Điền từ còn thiếu vào câu ca dao: \"Gần mực thì đen, gần đèn thì...\"gì?");
        answers = new ArrayList<>();
        answer = new Answer();
        answer.setContent("Lóa");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Chói");
        answer.setCorrect(false);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Sáng");
        answer.setCorrect(true);
        answers.add(answer);
        answer = new Answer();
        answer.setContent("Tối");
        answer.setCorrect(false);
        answers.add(answer);
        question.setAnswers(answers);
        questionList.add(question);
    }
}
