package com.xxxx.manager.vo;

import com.xxxx.manager.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserLeague extends User {
    private Integer score;

    public UserLeague() {
    }

    public UserLeague(Integer userId, String userName, Integer score) {
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
