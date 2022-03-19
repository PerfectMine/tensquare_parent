package com.tensquare.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 37269 on 2018/6/12.
 */
@Component
public class ManagerFilter extends ZuulFilter {


    @Autowired
    private JwtUtil jwtUtil;

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

        //判断是否是登陆的地址
        String url = request.getRequestURL().toString();
        System.out.println("url:"+url);
        if(url.indexOf("/admin/login")>0){
            //登陆地址直接转发
            System.out.println("登陆地址直接转发");
            return null;
        }


        String authHeader = request.getHeader("Authorization");//获取头信息
        System.out.println("Authorization:"+authHeader);
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null){
                if("admin".equals(claims.get("roles"))){
                    requestContext.addZuulRequestHeader("Authorization",authHeader);
                    return null;
                }
            }
        }
        //不合法请求？
        System.out.println("终止转发....");
        requestContext.setSendZuulResponse(false);//false表示不转发
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");

        return null;
    }
}
