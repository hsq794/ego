package com.xxxx.manager.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.pojo.User;
import com.xxxx.manager.vo.UserLeague;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserLeagueService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 积分key
     */
    public String formatZSetKey(){
        String yyyyMM= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return String.format("rank:%s",yyyyMM);
    }

    /**
     * 添加用户积分信息
     */
    public BaseResult addOrUpdatePoints(Integer userId, Integer score){
        //判断用户
        if(null==userId){
            return BaseResult.error();
        }
        //获取key
        String key=formatZSetKey();
        //添加用户积分，使用SortedSet 插入积分自动排名
        redisTemplate.opsForZSet().incrementScore(key,userId,score);
        return BaseResult.success();
    }

    /**
     * 获取个人的排行信息，如果大于999显示999+
     */
    public String findCurrentMonthRank(Integer userId){
        if(null==userId){
            return "用户不存在!";
        }
        //获取key
        String key=formatZSetKey();
        //根据用户id 获取排名信息并反转
        Long rank  = redisTemplate.opsForZSet().reverseRank(key, userId);
        //判断是否显示999+
        //分析：999+ 都是哪些用户
        if(null == rank || rank +1 >999){
            return "999+";
        }
        return String.valueOf(rank+1);
    }

    /**
     * 获取排行榜多少名
     */
    public List<UserLeague> findTop(int limit){
        //获取key
        String key=formatZSetKey();
        //获取0~limit的用户信息
        Set<Integer> userIds=redisTemplate.opsForZSet().reverseRange(key,0,limit-1);
        return userIds.stream().map(userId->{
            //根据key用户id获取用户信息
            Object userMap = redisTemplate.opsForHash().get("user", String.valueOf(userId));
            //反序列化为user对象
            ObjectMapper objectMapper=new ObjectMapper();
            User user=objectMapper.convertValue(userMap,User.class);
            //创建UserLeague对象
            UserLeague userLeague=new UserLeague();
            //复制对象
            BeanUtils.copyProperties(user,userLeague);
            //根据rank:年月 和用户id 获取用户积分信息
            Double scores=redisTemplate.opsForZSet().score(key,userId);
            if(null==scores){
                scores=0D;
            }
            //设置用户积分
            userLeague.setScore(scores.intValue());
            return  userLeague;

        }).collect(Collectors.toList());

    }
}
