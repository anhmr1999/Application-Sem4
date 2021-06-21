package com.project.game.entity;

import java.util.List;

public class Question extends Entity{
    private String content;
    private String subject;
    private Integer userId;
    private User User;
    private List<Answer> answers;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
