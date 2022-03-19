package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 交友控制层
 */
@RestController
@RequestMapping("/friend")
public class FriendController {


    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;


    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        //获取当前登陆的用户
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }

        if("1".equals(type)){  //如果是添加好友操作
            if(friendService.addFriend( claims.getId(), friendid)==0 ){
                return  new Result(false, StatusCode.REPERROR,"你已经添加了此好友");
            }
        }else{  //添加非好友
            friendService.addNoFriend(claims.getId(),friendid);
        }
        return new  Result(true, StatusCode.OK,"成功操作");
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value="/{friendid}" ,method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //获取当前登陆的用户
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR,"无权访问");
        }
        friendService.deleteFriend(claims.getId(),friendid);// 执行删除
        return new Result(true,StatusCode.OK,"删除成功");

    }

}
