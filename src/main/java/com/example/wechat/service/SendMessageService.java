package com.example.wechat.service;

import com.example.wechat.msg.TextMessage;
import com.example.wechat.util.HttpRequest;
import com.example.wechat.util.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SendMessageService {
    public static void sendTextMessage(HttpServletResponse response, TextMessage message){
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(MessageUtil.textMessageToXml(message));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //发送图文客服消息
    public static void sendCustomerImageMsg(String accessToken, String mediaId, String openId){
//        String accessToken = "15_aDNMiLLB5cZq6xK77vuU-jFmqLlSFXufRyVzEVhNbISW_7TkFjPabFumOhMe_v25cySV1rl3Cq9TdrkRDcG6U8SNl6sp2F25u2NoQJENdeVHINYd4Md83fKwkVSEzCJCTpykovh2-qjlkadJJIGgADACBZ";
        String baseUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
        String param = "{\"touser\":\""+openId+"\",\"msgtype\":\"image\",\"image\":{\"media_id\":\""+mediaId+"\"}}";
        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println("发送了客服消息"+result);
    }
}
