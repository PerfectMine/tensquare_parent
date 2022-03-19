package com.tensquare.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 37269 on 2018/6/12.
 */
@RefreshScope
@RestController
public class TestController {

    @Value("${sms.ip}")
    private String ip;

    @RequestMapping("ip")
    public String ip(){
        return ip;
    }

}
