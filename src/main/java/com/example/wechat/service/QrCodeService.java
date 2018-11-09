package com.example.wechat.service;

import com.alibaba.fastjson.JSON;
import com.example.wechat.util.DownLoadImage;
import com.example.wechat.util.HttpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class QrCodeService {

    public static void sendQrcodeToUser(String accessToken, String opendId){
        String ticket = createQrCode(accessToken, opendId);
        boolean isDownLoad = downLoadQrcode(ticket, opendId);
        if(isDownLoad){
            String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";

            try {
                String result = HttpRequest.postImage(url, new File("qrcodeimages/fission_"+opendId+".png"));
                Map maps = (Map)JSON.parse(result);
                String mediaId = (String) maps.get("media_id");

                if(mediaId!=null){
                    System.out.println("mediaId = "+mediaId);
                    SendMessageService.sendCustomerImageMsg(accessToken,mediaId,opendId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String createQrCode(String accessToken, String opendId){
        String baseUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
        String param = "{\"expire_seconds\":604800,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""+opendId+"\"}}}";

        String result = HttpRequest.sendPost(baseUrl,param);
        Map maps = (Map)JSON.parse(result);
        String ticket = (String) maps.get("ticket");

        return ticket;
    }

    private static boolean downLoadQrcode(String ticket, String filename){
        boolean flag = true;
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
        String pre = "fission_";
        try {
            DownLoadImage.download(url, pre+filename);
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }


}
