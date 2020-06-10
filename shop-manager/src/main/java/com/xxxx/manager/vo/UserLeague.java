package com.xxxx.manager.vo;

import com.xxxx.manager.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLeague extends User {
    private Integer score;

}
