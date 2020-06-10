package com.xxxx.manager.pojo;

public class League extends User {
    private Integer score;

    public League() {

    }

    public League(Integer userId, String userName, Integer score) {
        super(userId, userName);
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
