package com.xxxx.manager.service;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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

        List<Integer> userIdList = Stream.of(users).map(user -> {
            //将数据存到redis
            redisTemplate.opsForHash().put("user", user.getUserId(), user);
            return user.getUserId();
        }).collect(Collectors.toList());

        userIdList.forEach(userId->{
            //生成随机积分
            int score = ThreadLocalRandom.current().nextInt(100);
            addcore(userId, score);

        });
        return userIdList.size()>0?BaseResult.success():BaseResult.error();
    }

    /**
     * 添加用户积分
     * @param userId
     * @param score
     * @return
     */
    public String addcore(Integer userId,Integer score){
        if(StringUtils.isEmpty(userId)){
            return "error";
        }
        if(StringUtils.isEmpty(score)){
            return "error";
        }
        //获取积分key
        String key = getScoreKey();
        //添加用户积分到redis并排序
        redisTemplate.opsForZSet().incrementScore(key, userId, score);
        return "success";
    }

    /**
     * 积分key
     */
    public String getScoreKey(){
        String yyyyMM = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return String.format("rank:%s",yyyyMM);
    }

}
