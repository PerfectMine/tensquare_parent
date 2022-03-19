package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交友服务类
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    @Transactional
    public int addFriend(String userid,String friendid){
        //判断好友表中是否存在该记录
        if( friendDao.selectCount(userid,friendid)>0){
            return 0;//0 表示已经添加过此好友
        }

        //向交友表中插入记录
        Friend friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方是否喜欢了你，如果存在喜欢的记录，修改islike的值 （2条）
        if( friendDao.selectCount(friendid,userid)>0 ){//如果对方喜欢你
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }

        //更改粉丝数
        userClient.incFanscount(friendid,1);
        //更改关注数
        userClient.incFollowcount(userid,1);

        return 1;//表示成功
    }


    /**
     * 添加非好友
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     *  删除好友
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){

        friendDao.deleteFriend(userid,friendid);//删除好友表中的记录
        friendDao.updateLike(friendid,userid,"0");//修改互粉状态
        addNoFriend(userid,friendid);//添加到非好友列表中

        userClient.incFanscount(friendid,-1);//减少对方的粉丝数
        userClient.incFollowcount(userid,-1);//减少自己的关注数



    }

}
