package com.tensquare.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 37269 on 2018/6/12.
 */
@Component
public class WebFilter extends ZuulFilter {


    @Override
    public String filterType() {//过滤器类型
        return "pre";//前置过滤器
    }

    @Override
    public int filterOrder() {//过滤器的执行顺序，数字越大，越靠后执行
        return 0;
    }

    @Override
    public boolean shouldFilter() { //过滤器的开关
        return true;//表示开启
    }

    @Override
    public Object run() throws ZuulException {//过滤器的执行逻辑
        System.out.println("经过了zuul过滤器");

        RequestContext requestContext=RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String authHeader = request.getHeader("Authorization");//获取头信息
        System.out.println("Authorization:"+authHeader);
        if(authHeader!=null){
            requestContext.addZuulRequestHeader("Authorization",authHeader);
        }
        return null;
    }
}
