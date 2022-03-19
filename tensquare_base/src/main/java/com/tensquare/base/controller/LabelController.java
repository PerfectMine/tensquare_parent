package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 37269 on 2018/6/2.
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){

        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll()  );
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        System.out.println("No.2");
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id)  );
    }

    /**
     *  增加
     * @param label
     * @return
     */
    @RequestMapping( method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     * @param label
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Label label,@PathVariable String id){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK ,"查询成功",list);
    }

    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap,@PathVariable int page,@PathVariable int size){
        Page<Label> pageList = labelService.findSearch(searchMap, page, size);
        PageResult pageResult=new PageResult( pageList.getTotalElements(),pageList.getContent() );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }



}
