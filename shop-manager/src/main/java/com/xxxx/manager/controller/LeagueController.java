package com.xxxx.manager.controller;

import com.xxxx.common.result.BaseResult;
import com.xxxx.manager.service.UserLeagueService;
import com.xxxx.manager.service.UserService;
import com.xxxx.manager.vo.UserLeague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("userLeague")
public class LeagueController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLeagueService  userLeagueService;

    /**
     * 添加用户数据并随机生成积分
     * @return
     */
    @RequestMapping("addUser")
    @ResponseBody
    public BaseResult addUser(){
        return userService.addUser();
    }

    /**
     *添加或更新积分
     * @param userId
     * @param score
     * @return
     */
    @RequestMapping("addOrUpdate")
    @ResponseBody
    public BaseResult addOrUpdatePoints(Integer userId,Integer score){
        return userLeagueService.addOrUpdatePoints(userId,score);
    }

    /**
     * 获取个人的排行信息，如果大于999显示999+
     * @param userId
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    public BaseResult findCurrentMonthRank(Integer userId){
        String rank = userLeagueService.findCurrentMonthRank(userId);
        System.out.println("id为 "+userId+" 的用户个人排行为："+rank);
        if(StringUtils.isEmpty(rank)){
            return BaseResult.error();
        }else {
            return  BaseResult.success();
        }
    }

    /**
     * 获取排行榜前几名
     * @param limit
     */
    @RequestMapping("findTop")
    @ResponseBody
    public BaseResult findTop(int limit){
        List<UserLeague> topList = userLeagueService.findTop(limit);
        System.out.println("排行榜前 "+limit+" 名的用户为：");
        topList.forEach(user->{
            System.out.println("用户名为："+user.getUserName()+"，积分为："+user.getScore());
        });

        if(CollectionUtils.isEmpty(topList)){
            return BaseResult.error();
        }else {
            return  BaseResult.success();
        }
    }

}
