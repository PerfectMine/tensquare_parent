package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 吐槽控制层
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 查询全部记录
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /**
     *  根据ID查询吐槽
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     *  增加
     * @param spit
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     *  修改
     * @param spit
     * @return
     */
    @RequestMapping( value="/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit ,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     *  删除
     * @param id
     * @return
     */
    @RequestMapping( value="/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据吐槽ID查询评论
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping( value="/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentid, page, size);
        PageResult pageResult=new PageResult(pageList.getTotalElements(), pageList.getContent() );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

    /**
     *  点赞
     * @param id
     * @return
     */
    @RequestMapping(value="/thumbup/{id}",method = RequestMethod.PUT)
    public Result updateThumbup( @PathVariable String id){
        spitService.updateThumbup(id);
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}
