package com.example.wechat.service;

import com.example.wechat.msg.TempMessage;
import com.example.wechat.msg.TempMessageData;
import com.example.wechat.msg.TextMessage;
import com.example.wechat.util.Config;
import com.example.wechat.util.HttpRequest;
import com.example.wechat.util.MessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
        String baseUrl = Config.SEND_CUSTOMER_MSG+"?access_token="+accessToken;
        String param = "{\"touser\":\""+openId+"\",\"msgtype\":\"image\",\"image\":{\"media_id\":\""+mediaId+"\"}}";
        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println("发送了图片客服消息"+result);
    }

    //发送文本客服消息
    public static void sendCustomerTextMsg(String accessToken, String content, String openId){
        String baseUrl = Config.SEND_CUSTOMER_MSG+"?access_token="+accessToken;
        String param = "{\"touser\":\""+openId+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+content+"\"}}";
        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println("发送了文本客服消息"+result);
    }


    //发送模板消息
    public static void sendTempMsg(String accessToken, String param){
        String baseUrl = Config.SEND_TEMP_MSG+"?access_token="+accessToken;
        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println(result);
    }

    //发送成功邀请的裂变模板消息
    public static void sendFissionTempMsg(String accessToken, String tempId, String openId, String subscribeName) throws JsonProcessingException {
        String baseUrl = Config.SEND_TEMP_MSG+"?access_token="+accessToken;
        TempMessageData data = new TempMessageData();
        data.setValue("恭喜你成功邀请了一人");
        data.setColor("#173177");
        TempMessageData data2 = new TempMessageData();
        data2.setValue(subscribeName+"通过您分享的二维码关注了公众号");
        data2.setColor("#173177");
        Map<String, TempMessageData> map = new HashMap<String, TempMessageData>();
        map.put("first", data);
        map.put("keyword1", data2);

        TempMessage tempMessage = new TempMessage();
        tempMessage.setTouser(openId);
        tempMessage.setTemplate_id(tempId);
        tempMessage.setData(map);
        ObjectMapper mapper = new ObjectMapper();
        String param = mapper.writeValueAsString(tempMessage);

        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println(result);
    }

}
