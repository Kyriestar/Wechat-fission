package com.example.wechat.dispatcher;

import com.alibaba.fastjson.JSON;
import com.example.wechat.msg.TextMessage;
import com.example.wechat.service.SendMessageService;
import com.example.wechat.util.HttpRequest;
import com.example.wechat.util.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class EventDispatcher {
    public static String processEvent(Map<String, String> map, HttpServletResponse response) {
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
            System.out.println("==============这是关注事件！");
            TextMessage txtmsg = new TextMessage();
            txtmsg.setToUserName(map.get("FromUserName"));
            txtmsg.setFromUserName(map.get("ToUserName"));
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType("text");
            //处理自己扫自己情况
            if(map.get("EventKey").substring(8).equals(map.get("FromUserName"))){
                System.out.print("扫描了自己！");
                response.setStatus(400);
                return null;
            }

            if(map.get("EventKey")!=null){
                String openid = map.get("EventKey").substring(8);
                System.out.print("==="+map.get("EventKey")+openid);
                String info = HttpRequest.getUserInfo(openid);
                Map maps = (Map) JSON.parse(info);
                String nickname = (String) maps.get("nickname");
                txtmsg.setContent("welcome ！ - 你成功通过扫描 "+nickname+" 的二维码进行的关注");
            }else{
                txtmsg.setContent("welcome ！");
            }

            //发送欢迎消息
            SendMessageService.sendTextMessage(response,txtmsg);

        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");

            TextMessage txtmsg = new TextMessage();
            txtmsg.setToUserName(map.get("FromUserName"));
            txtmsg.setFromUserName(map.get("ToUserName"));
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType("text");
            txtmsg.setContent("你扫描了"+map.get("EventKey")+"分享的二维码");
            //发送消息
            SendMessageService.sendTextMessage(response,txtmsg);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件
            System.out.println("==============这是自定义菜单 View 事件！");
        }

        return null;
    }
}
