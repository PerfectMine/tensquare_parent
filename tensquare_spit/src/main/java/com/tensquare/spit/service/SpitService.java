package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 吐槽服务层
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部记录
     * @return
     */
    public List<Spit> findAll(){
       return  spitDao.findAll();
    }

    /**
     *  根据ID查询吐槽实体类
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 发布吐槽（或吐槽评论）
     * @param spit
     */
    public void add(Spit spit){
        spit.set_id( idWorker.nextId()+"" );
        spit.setPublishtime(new Date());//发布日期
        spit.setUserid("1");//当前登陆人的ID  (后期我们会对此处进行修改)
        spit.setNickname("");//当前登陆人的昵称 (后期我们会对此处进行修改)
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态

        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){//如果存在上级ID,评论
            Spit parentSpit = spitDao.findById(spit.getParentid()).get();//被评论的吐槽
            parentSpit.setComment(parentSpit.getComment()+1);// 评论数+1
            spitDao.save(parentSpit);
        }
        spitDao.save(spit);
    }
    /**
     * 修改吐槽
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 根据上级ID查询吐槽信息
     * @param parentid 上级ID
     * @param page 页码
     * @param size 页大小
     * @return
     */
    public Page<Spit> findByParentid(String parentid,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid,pageRequest);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 点赞
     * @param id
     */
    public void updateThumbup(String id){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
