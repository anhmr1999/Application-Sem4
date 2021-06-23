package com.project.game.entity;

public class Answer extends Entity{
    private String content;
    private boolean correct;
    private int questionId;
    private Question question;

    public Answer() {
        super(0);
    }

    public Answer(Integer id, String content, boolean correct, int questionId) {
        super(id);
        this.content = content;
        this.correct = correct;
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "("+content+","+(correct?1:0)+","+questionId+")";
    }
}