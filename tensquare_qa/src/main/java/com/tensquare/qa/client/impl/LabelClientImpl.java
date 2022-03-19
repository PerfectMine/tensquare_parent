package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by 37269 on 2018/6/11.
 */
@Component
public class LabelClientImpl implements LabelClient   {

    public Result findById(String id){
        return new Result(false, StatusCode.REMOTEERROR,"熔断器启动了");
    }
}
