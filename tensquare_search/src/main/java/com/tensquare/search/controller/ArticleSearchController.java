package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 37269 on 2018/6/6.
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK,"增加成功");
    }


    @RequestMapping(value="/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleOrContentLike(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> pageList= articleSearchService.findByTitleOrContentLike(keywords,page,size);
        PageResult pageResult=new PageResult(pageList.getTotalElements(),pageList.getContent() );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

}
