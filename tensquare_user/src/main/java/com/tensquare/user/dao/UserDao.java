package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);

    /**
     *  修改粉丝数
     * @param userid
     * @param x
     */
    @Modifying
    @Query("update User u set u.fanscount=u.fanscount+?2 where u.id=?1")
    public void incFanscount(String userid,int x);


    /**
     *  修改关注数
     * @param userid
     * @param x
     */
    @Modifying
    @Query("update User u set u.followcount=u.followcount+?2 where u.id=?1")
    public void incFollowcount(String userid,int x);

}
