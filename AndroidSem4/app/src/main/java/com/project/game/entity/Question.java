package com.project.game.entity;

import java.util.List;

public class Question extends Entity{
    private String content;
    private String subject;
    private Integer userId;
    private User user;
    private List<Answer> answers;

    public Question() {
        super(0);
    }

    public Question(Integer id, String content, String subject, Integer userId) {
        super(id);
        this.content = content;
        this.subject = subject;
        this.userId = userId;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
