package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 37269 on 2018/6/11.
 */
@FeignClient(value="tensquare-user")
public interface UserClient {

    /**
     * 修改粉丝数
     * @param userid
     * @param x
     */
    @RequestMapping(value="/user/incfans/{userid}/{x}" ,method = RequestMethod.POST)
    public void incFanscount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /**
     * 修改关注数
     * @param userid
     * @param x
     */
    @RequestMapping(value="/user/incfollow/{userid}/{x}" ,method = RequestMethod.POST)
    public void incFollowcount(@PathVariable("userid") String userid, @PathVariable("x") int x);

}
