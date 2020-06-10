package com.xxxx.manager.service;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 添加用户
     * @return
     */

    public BaseResult addUser() {
        User[] users = new User[]{
                new User(1,"张一"),
                new User(2,"张二"),
                new User(3,"张三"),
                new User(4,"张四"),
                new User(5,"张五"),
                new User(6,"赵六"),
                new User(7,"王七"),
                new User(8,"刘八"),
                new User(9,"李九"),
                new User(10,"梁十")
        };
        //清空数据库
        redisTemplate.getConnectionFactory().getConnection().flushDb();
        List<Integer> userList = Stream.of(users).map(user -> {
            redisTemplate.opsForHash().put("user", user.getUserId(), user);
            return user.getUserId();
        }).collect(Collectors.toList());

        return null;
    }
}
