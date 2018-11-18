package com.example.wechat.service;

import com.example.wechat.util.Config;
import com.example.wechat.util.DownLoadImage;
import com.example.wechat.util.HttpRequest;
import com.example.wechat.util.JsonTool;
import java.io.File;
import java.io.IOException;

public class QrCodeService {
    public static final String pre = "fission_";

    public static void sendQrcodeToUser(String accessToken, String opendId){
        String ticket = createQrCode(accessToken, opendId);
        boolean isDownLoad = downLoadQrcode(ticket, opendId);
        if(isDownLoad){
            String url = Config.UPLOAD_MEDIA+"?access_token="+accessToken+"&type=image";

            try {
                String result = HttpRequest.postImage(url, new File("qrcodeimages/"+pre+opendId+".png"));
//                Map maps = (Map)JSON.parse(result);
//                String mediaId = (String) maps.get("media_id");

                String mediaId =(String)JsonTool.json2Map(result).get("media_id");

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
        String baseUrl=Config.QRCODE_CREATE+"?access_token="+accessToken;
        String param = "{\"expire_seconds\":604800,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""+opendId+"\"}}}";

        String result = HttpRequest.sendPost(baseUrl,param);
//        Map maps = (Map)JSON.parse(result);
//        String ticket = (String) maps.get("ticket");

        String ticket = (String) JsonTool.json2Map(result).get("ticket");

        return ticket;
    }

    private static boolean downLoadQrcode(String ticket, String filename){
        boolean flag = true;
        String url = Config.QRCODE_SHOW+"?ticket="+ticket;

        try {
            DownLoadImage.download(url, pre+filename);
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }


}
