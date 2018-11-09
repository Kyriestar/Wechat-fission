package com.example.wechat.dispatcher;

import com.example.wechat.msg.TextMessage;
import com.example.wechat.service.QrCodeService;
import com.example.wechat.service.SendMessageService;
import com.example.wechat.util.Config;
import com.example.wechat.util.HttpRequest;
import com.example.wechat.util.MessageUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public class MsgDispatcher {
    public static String processMsg(Map<String, String> map, HttpServletResponse response){
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
            System.out.println("==============这是文本消息！");
            String content = map.get("Content");
            String toUserName =map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");

            if(content.equals("参与活动")){
                TextMessage txtmsg = new TextMessage();
                txtmsg.setToUserName(map.get("FromUserName"));
                txtmsg.setFromUserName(map.get("ToUserName"));
                txtmsg.setCreateTime(new Date().getTime());
                txtmsg.setMsgType("text");
                txtmsg.setContent("参与活动成功");

                SendMessageService.sendTextMessage(response,txtmsg);
                //fromUserName is openId
                QrCodeService.sendQrcodeToUser(Config.ACCESS_TOKEN, fromUserName);
            }

            if(content.equals("二维码")){

            }

            if(content.equals("客服文本")){
                sendCustomerMsg2();
            }

        }
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
            System.out.println("==============这是图片消息！");
        }
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
            System.out.println("==============这是链接消息！");
        }
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
            System.out.println("==============这是语音消息！");
        }
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
            System.out.println("==============这是位置消息！");
        }
        if(map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
            System.out.println("==============这是事件消息！");
        }
        return null;
    }

    private static void sendCustomerMsg2(){
        String accessToken = "15_aDNMiLLB5cZq6xK77vuU-jFmqLlSFXufRyVzEVhNbISW_7TkFjPabFumOhMe_v25cySV1rl3Cq9TdrkRDcG6U8SNl6sp2F25u2NoQJENdeVHINYd4Md83fKwkVSEzCJCTpykovh2-qjlkadJJIGgADACBZ";
        String baseUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
        String param = "{\"touser\":\"o1BWk0utKViGg5DEPeMgk-OrFrOA\",\"msgtype\":\"text\",\"text\":{\"content\":\"customer text message\"}}";
        String result = HttpRequest.sendPost(baseUrl,param);
        System.out.println(result);
    }



}
