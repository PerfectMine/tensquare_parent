/*
package com.tensquare.sms;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

*/
/**
 * Created by 37269 on 2018/6/8.
 *//*

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private Environment env;

    @RabbitHandler
    public void sendSms(Map<String,String> map){
        System.out.println("手机号:"+map.get("mobile"));
        System.out.println("验证码："+map.get("code"));
        String param="{\"number\":\""+map.get("code")+"\"}";
        System.out.println(param);

        try {
            SendSmsResponse response = smsUtil.sendSms(map.get("mobile"),
                    env.getProperty("aliyun.sms.template_code"),
                    env.getProperty("aliyun.sms.sign_name"),
                    param);
            System.out.println("短信发送成功！"+response.getCode()+"  "+response.getMessage()+"  "+response.getBizId());
        } catch (ClientException e) {
            e.printStackTrace();
        }


    }

}
*/
