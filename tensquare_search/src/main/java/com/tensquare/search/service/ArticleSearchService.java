package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by 37269 on 2018/6/6.
 */
@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 增加文章
     * @param article
     */
    public void add(Article article){
        articleSearchDao.save(article);
    }

    /**
     * 查询
     */
    public Page<Article> findByTitleOrContentLike(String keywords, int page, int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return articleSearchDao.findByTitleOrContentLike(keywords,keywords,pageRequest);
    }

}
