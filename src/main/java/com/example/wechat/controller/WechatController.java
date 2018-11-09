package com.example.wechat.controller;

import com.example.wechat.dispatcher.EventDispatcher;
import com.example.wechat.dispatcher.MsgDispatcher;
import com.example.wechat.util.CheckUtil;
import com.example.wechat.util.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
public class WechatController {
    private final static String token = "clxclx";

    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        String signature = request.getParameter("signature");
        String timeStamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            //开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
            if (CheckUtil.checkSignature(token, timeStamp, nonce).equals(signature)) {
                //作出响应，即原样返回随机字符串
                out.println(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response){


        System.out.println("this is post method");

        try {
            Map<String, String> map = MessageUtil.xml2Map(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }

            String msgtype=map.get("MsgType");
            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
                EventDispatcher.processEvent(map, response); //进入事件处理
            }else{
                MsgDispatcher.processMsg(map, response); //进入消息处理
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/test")
    public String test(){
        return "hello test";
    }

}
