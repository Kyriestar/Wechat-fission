package com.example.wechat;

import com.alibaba.fastjson.JSON;
import com.example.wechat.msg.TempMessage;
import com.example.wechat.msg.TempMessageData;
import com.example.wechat.service.QrCodeService;
import com.example.wechat.service.SendMessageService;
import com.example.wechat.util.Config;
import com.example.wechat.util.DownLoadImage;
import com.example.wechat.util.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTests {

    @Test
    public void createQrcode() throws IOException {
        // https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

//		String result = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token","grant_type=client_credential&appid=wxdd95228d72f2bcac&secret=9387de14770c7f92cfdb319dcb6a9ff0");
//		System.out.println(result);

        String accessToken = "15_h8WGWzbdXwN2Gc_ZJCsPCRzxvxvpz9Eaf4ms7rpP1ChCo1t262UKcVTNH3VamF-jTBjVT8bbctLn5D9aWLBiVg8LouO0AZNfctgUTE-9E_SEfJowGqCnl0e6xG3PTTeaH1E-KwMmLY4S67M7BDZfACAUWS";
        String sceneStr = "o1BWk0utKViGg5DEPeMgk-OrFrOA";

//		String baseUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
//		String param = "{\"expire_seconds\":604800,\"action_name\":\"QR_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\" "+sceneStr+" \"}}}";
//		String result = HttpRequest.sendPost(baseUrl,param);
//		System.out.println(result);
//		Map maps = (Map) JSON.parse(result);
//		String ticket = (String) maps.get("ticket");
//		System.out.println(ticket);
        QrCodeService.sendQrcodeToUser(accessToken, sceneStr);
    }

    @Test
    public void getQrCode() {
        byte[] result = HttpRequest.getImage("https://mp.weixin.qq.com/cgi-bin/showqrcode", "ticket=gQF67zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNVRnZGNwa2xlQWkxWGhPRzFyY00AAgRReOFbAwSAOgkA");
        System.out.println(result.length);

    }

    @Test
    public void downloadImage() throws Exception {
        DownLoadImage.download("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF67zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyNVRnZGNwa2xlQWkxWGhPRzFyY00AAgRReOFbAwSAOgkA", "erweima");
    }

    @Test
    public void sendImage() {
        File file = new File("D:\\xiaoxingWork\\spring-boot-learning\\wechat\\erweima.png");
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=15_ntlKN5caKUb5QA0-Mzw8x7nfLavVw5rymTPMyi0NSwojUhJ3Pc89YvKDMVqfyKTjgomY-taM8nzFoiaRuHGsGNhE56qWsv_VG1beapU8cOJavibKZxttlBdIWiFzmcSW9U_i3rGlGm6znMt1BCGbAHAKLP&type=image";
        try {
            String s = HttpRequest.postImage(url, file);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNickName() {
        //https://api.weixin.qq.com/cgi-bin/user/info?access_token=".$access_token."&openid=".$fromUsername."&lang=zh_CN
        String openid = "o1BWk0utKViGg5DEPeMgk-OrFrOA";
        String url = "https://api.weixin.qq.com/cgi-bin/user/info";
        String accesstoken = "15_OIi6JJ6Tdt31fjM4dzkADZDA2HhBQBJpzDE_yMduIrd0S2tF33btSgOmQWexdKKtyYb4dBVbW-G9IUlLVBVAJJq70bR3TIoCR6QFAgyxjmkE_ggCqgmdv0zsX7iDBg9iSzr-V2sKX6n9ZTYzSEUbAIAOAQ";
        String param = "access_token=" + accesstoken + "&openid=" + openid + "&lang=zh_CN";
        String result = HttpRequest.sendGet(url, param);
        System.out.println(result);

    }

    @Test
    public void testObjectMapper() throws IOException {
//        String GENERIC_BINDING = "{\"key1\":\"str1\",\"key2\":\"str2\"}";
        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> map1 = mapper.readValue(GENERIC_BINDING, new TypeReference<HashMap<String, String>>() {
//        });//readValue到一个范型数据中.
//
//        Map map2 = mapper.readValue(GENERIC_BINDING,Map.class);
//        System.out.println("map2:"+map2);
//
//        HashMap<String, String> map3 = mapper.readValue(GENERIC_BINDING,HashMap.class);
//        String str = map3.get("key1");
//        System.out.println(str);

        String jsonstr = "{\"verified\":false,\"name\":{\"last\":\"Hankcs\",\"first\":\"Joe\"},\"userImage\":\"Rm9vYmFyIQ==\",\"gender\":\"MALE\"}";
        Map<String,Object> userData = mapper.readValue(jsonstr, Map.class);
        System.out.println(userData);
        System.out.println(userData.get("name"));

        userData = new HashMap<String,Object>();
        Map<String,String> nameStruct = new HashMap<String,String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Hankcs");
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        System.out.println(mapper.writeValueAsString(userData));
    }

    @Test
    public void testSendTempMsg() throws JsonProcessingException {
        TempMessageData data = new TempMessageData();
        data.setValue("恭喜你成功邀请了一人");
        data.setColor("#173177");
        TempMessageData data2 = new TempMessageData();
        data2.setValue("xxxx");
        data2.setColor("#173177");
        Map<String, TempMessageData> map = new HashMap<String, TempMessageData>();
        map.put("first", data);
        map.put("keyword1", data2);

        TempMessage tempMessage = new TempMessage();
        tempMessage.setTouser("o1BWk0utKViGg5DEPeMgk-OrFrOA");
        tempMessage.setTemplate_id("vskiulW0isAPyyS9i49-RhzRq91k_rNjF-KvEOqoTwY");
        tempMessage.setData(map);
        ObjectMapper mapper = new ObjectMapper();
        String param = mapper.writeValueAsString(tempMessage);
        SendMessageService.sendTempMsg(Config.ACCESS_TOKEN,param);

    }
}
